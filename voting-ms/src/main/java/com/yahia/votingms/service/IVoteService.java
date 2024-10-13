package com.yahia.votingms.service;

import com.yahia.votingms.dto.VoteDto;
import com.yahia.votingms.entity.Vote;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

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



}
