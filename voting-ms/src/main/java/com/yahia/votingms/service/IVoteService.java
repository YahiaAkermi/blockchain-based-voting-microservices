package com.yahia.votingms.service;

import com.yahia.votingms.dto.VoteDto;
import com.yahia.votingms.dto.VoteDtoWithId;
import com.yahia.votingms.entity.Vote;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IVoteService {

    /**
     *this method will create vote for a particular voter
     *@param voteDto -  VoteDto  object
     */

    void createVote(VoteDto voteDto);

    /**
     *this method will retrieve all votes for particular session by pagination
     * @param votingSessionId - Long
     * @param pageNum - Integer
     * @param pageSize - Integer
     */

    Page<Vote> fetchAllVotesBySession(Long votingSessionId, Integer pageNum, Integer pageSize);

    /**
     *this method will retrieve all votes for particular candidate
     * @param condidateId - Long
     * @param pageNum - Integer
     * @param pageSize - Integer
     */

    Page<Vote> fetchAllVotesByCandidate(Long condidateId, Integer pageNum, Integer pageSize);


    /**
     *this method will retrieve all votes for particular voter
     * @param voterId - Long
     * @param pageNum - Integer
     * @param pageSize - Integer
     */

    Page<Vote> fetchAllVotesByVoter(Long voterId, Integer pageNum, Integer pageSize);


    /**
     * this method will retrieve  particular vote
     *
     * @param voteId - String
     */
    VoteDtoWithId fetchVote(String voteId);


    /**
     * this method will retrun boolean to determine wether the vote has been updated or not
     * @param voteId - String
     * @param voteDto - VoteDto
     */
    boolean updateVote(String voteId,VoteDto voteDto);

    /**
     * this method will retrun boolean to determine wether the vote has been deleted or not
     * @param voteId - String
     */
    boolean deleteVote(String voteId);



}
