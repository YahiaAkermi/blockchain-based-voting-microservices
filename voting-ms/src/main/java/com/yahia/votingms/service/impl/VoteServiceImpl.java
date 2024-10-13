package com.yahia.votingms.service.impl;

import com.yahia.votingms.dto.VoteDto;
import com.yahia.votingms.dto.VoteDtoWithId;
import com.yahia.votingms.entity.Vote;
import com.yahia.votingms.exception.ResourceNotFoundException;
import com.yahia.votingms.mapper.VoteMapper;
import com.yahia.votingms.repository.VoteRepository;
import com.yahia.votingms.service.IVoteService;
import com.yahia.votingms.exception.VoteAlreadyCastedException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements IVoteService {

    private final VoteRepository voteRepository;


    /**
     * this method will create vote for a particular voter
     *
     * @param voteDto -  VoteDto  object
     */
    @Override
    public void createVote(VoteDto voteDto) {

        //0. i need to check if the voting session is open to vote after establishing communication between microservices


        //1.then I need to check if the user is eligible to vote when i start working on the communication between my ms

        //2.check if the voter has already voted in a specific session
        Optional<Vote> retrievedVote=voteRepository.findVoteByVoterIdAndVotingSessionId(voteDto.getVoterId(), voteDto.getVotingSessionId());

        if (retrievedVote.isPresent()){
         throw new VoteAlreadyCastedException(String.format("Vote has been already casted by %s in the following voting session %s"
                    ,voteDto.getVoterId(),voteDto.getVotingSessionId()));
        }




        //3. then i will map to Vote so i can store it in votes' collection
        voteRepository.save(VoteMapper.maptoVote(voteDto,new Vote()));

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
        Vote updatedVote=VoteMapper.maptoVote(voteDto,retrievedVote);

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
