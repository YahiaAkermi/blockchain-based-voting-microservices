package com.yahia.votingms.repository;

import com.yahia.votingms.entity.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends MongoRepository<Vote,String> {




    Optional<Vote> findVoteByVoterIdAndVotingSessionId(Long voterId, Long votingSessionId);


    Page<Vote>  findVotesByVotingSessionId(Long votingSessionId, Pageable pageable);

    boolean existsVoteByVotingSessionId(Long votingSessionId);



}
