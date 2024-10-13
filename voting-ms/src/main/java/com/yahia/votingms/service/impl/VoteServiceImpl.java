package com.yahia.votingms.service.impl;

import com.yahia.votingms.dto.VoteDto;
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
}
