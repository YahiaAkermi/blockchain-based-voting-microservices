package com.yahia.votingms.service.impl;

import com.yahia.votingms.dto.VoteDto;
import com.yahia.votingms.dto.VoteDtoWithId;
import com.yahia.votingms.dto.clientDtos.VotingSessionDtoWithId;
import com.yahia.votingms.entity.Vote;
import com.yahia.votingms.exception.CastedVoteRejectedException;
import com.yahia.votingms.exception.ResourceNotFoundException;
import com.yahia.votingms.mapper.VoteMapper;
import com.yahia.votingms.repository.VoteRepository;
import com.yahia.votingms.service.IVoteService;
import com.yahia.votingms.exception.VoteAlreadyCastedException;
import com.yahia.votingms.service.client.VmMsFeignClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements IVoteService {

    private final VoteRepository voteRepository;
    private final VmMsFeignClient vmMsFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(VoteServiceImpl.class);


    /**
     * this method will create vote for a particular voter
     *
     * @param voteDto -  VoteDto  object
     */
    @Override
    public void createVote(VoteDto voteDto) {

        // 0. Fetch voting session details
        VotingSessionDtoWithId votingSessionDtoWithId = vmMsFeignClient.fetchVotingSession(voteDto.getVotingSessionId()).getBody();

        // Assuming votingSessionEndDate is in UTC+2, convert it to UTC (adjust the ZoneId if necessary)
        ZonedDateTime votingSessionEndDate = votingSessionDtoWithId.getVotingSessionDto()
                .getEndDate().atZone(ZoneId.of("UTC+2")).withZoneSameInstant(ZoneId.of("UTC"));

        // Log the converted voting session end date in UTC
        logger.warn("Voting session end date (converted to UTC): " + votingSessionEndDate);

        // Get the current time as ZonedDateTime in UTC
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));

        // Log the current time in UTC
        logger.warn("Current time (UTC): " + now);

        // 1. Check if the voting session is closed
        if (votingSessionEndDate.isBefore(now)) {
            logger.warn("Voting session is closed. Cannot cast vote.");
            throw new CastedVoteRejectedException("Cannot cast this vote because the voting session is closed.");
        }

        // 2. Check if the voter has already voted in this session
        Optional<Vote> retrievedVote = voteRepository.findVoteByVoterIdAndVotingSessionId(voteDto.getVoterId(), voteDto.getVotingSessionId());

        if (retrievedVote.isPresent()) {
            throw new VoteAlreadyCastedException(String.format("Vote has already been cast by %s in voting session %s", voteDto.getVoterId(), voteDto.getVotingSessionId()));
        }

        // 3. Save the vote
        voteRepository.save(VoteMapper.maptoVote(voteDto, new Vote()));

        logger.info("Successfully cast vote for voterId: " + voteDto.getVoterId());
    }

    /**
     *this method will retrieve all votes for particular session by pagination
     * @param votingSessionId - Long
     * @param pageNum - Integer
     * @param pageSize - Integer
     */
    @Override
    public Page<Vote> fetchAllVotesBySession(Long votingSessionId, Integer pageNum, Integer pageSize) {

        //check if there is votes for the voting session
        boolean isThereAnyVoteForThisSession = voteRepository.existsVoteByVotingSessionId(votingSessionId);

        if (!isThereAnyVoteForThisSession) {
            throw new ResourceNotFoundException("no votes has been casted for the voting session that goes by the ID " + votingSessionId.toString());
        }

        //here i passed both page number and page size I can create multiple pageable where I can add sorting ass 3rd param
        Pageable pageableA =  PageRequest.of(pageNum, pageSize);

        return voteRepository.findVotesByVotingSessionId(votingSessionId, pageableA);
    }

    /**
     * this method will retrieve all votes for particular candidate
     *
     * @param condidateId - Long
     * @param pageNum     - Integer
     * @param pageSize    - Integer
     */
    @Override
    public Page<Vote> fetchAllVotesByCandidate(Long condidateId, Integer pageNum, Integer pageSize) {

        //check if user has cast any votes
        boolean isThereAnyVoteForThisSession = voteRepository.existsVoteByCondidateId(condidateId);

        if (!isThereAnyVoteForThisSession) {
            throw new ResourceNotFoundException("condidate with ID "+condidateId.toString()+" + haven't been voted on ");
        }

        //here i passed both page number and page size I can create multiple pageable where I can add sorting ass 3rd param
        Pageable pageableA =  PageRequest.of(pageNum, pageSize);

        return voteRepository.findVotesByCondidateId(condidateId, pageableA);
    }

    /**
     * this method will retrieve all votes for particular voter
     *
     * @param voterId  - Long
     * @param pageNum  - Integer
     * @param pageSize - Integer
     */
    @Override
    public Page<Vote> fetchAllVotesByVoter(Long voterId, Integer pageNum, Integer pageSize) {
        //check if user has cast any votes
        boolean isThereAnyVoteForThisSession = voteRepository.existsVoteByVoterId(voterId);

        if (!isThereAnyVoteForThisSession) {
            throw new ResourceNotFoundException("voter with ID "+voterId.toString()+" + haven't cast any votes yet  ");
        }

        //here i passed both page number and page size I can create multiple pageable where I can add sorting ass 3rd param
        Pageable pageableA =  PageRequest.of(pageNum, pageSize);

        return voteRepository.findVotesByVoterId(voterId, pageableA);
    }

    /**
     * this method will retrieve  particular vote
     *
     * @param voteId - String
     */
    @Override
    public VoteDtoWithId fetchVote(String voteId) {

        //check if the vote exists
        Vote retrievedVote=voteRepository.findById(voteId).orElseThrow(
                ()-> new ResourceNotFoundException("the request Vote under ther ID "+voteId+" doesn't exist ")
        );

        //map to it dto with id

        VoteDtoWithId voteDtoWithId=VoteMapper.maptoVoteDtoWithId(retrievedVote,new VoteDtoWithId());
        voteDtoWithId.setVoteDto(VoteMapper.maptoVoteDto(retrievedVote,new VoteDto()));

        return voteDtoWithId;
    }

    /**
     * this method will retrun boolean to determine wether the vote has been updated or not
     * @param voteId - String
     * @param voteDto - VoteDto
     */
    @Override
    public boolean updateVote(String voteId,VoteDto voteDto) {

        //check if the vote exists
        Vote retrievedVote=voteRepository.findById(voteId).orElseThrow(
                ()-> new ResourceNotFoundException("the request Vote under ther ID "+voteId+" doesn't exist ")
        );

        //map it to Vote
        Vote updatedVote=VoteMapper.maptoVote2(voteDto,retrievedVote);

        //finally we save in our db
        voteRepository.save(updatedVote);

        return true;
    }

    /**
     * this method will retrun boolean to determine wether the vote has been deleted or not
     * @param voteId - String
     */
    @Override
    public boolean deleteVote(String voteId) {

        //check if the vote exists
        Vote retrievedVote=voteRepository.findById(voteId).orElseThrow(
                ()-> new ResourceNotFoundException("the request Vote under ther ID "+voteId+" doesn't exist ")
        );

        //then delete from the db
       voteRepository.deleteById(voteId);

        return true;
    }
}
