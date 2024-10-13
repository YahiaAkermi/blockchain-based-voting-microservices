package com.yahia.votingms.mapper;

import com.yahia.votingms.dto.VoteDto;
import com.yahia.votingms.dto.VoteDtoWithId;
import com.yahia.votingms.entity.Vote;

public  class VoteMapper {

    public static Vote maptoVote(VoteDto voteDto,Vote vote){

        vote.setVoterId(voteDto.getVoterId());
        vote.setCondidateId(voteDto.getCondidateId());
        vote.setVotingSessionId(voteDto.getVotingSessionId());


        return vote;
    }


    public static VoteDto maptoVoteDto(Vote vote,VoteDto voteDto){

        voteDto.setVoterId(vote.getVoterId());
        voteDto.setCondidateId(vote.getCondidateId());
        voteDto.setVotingSessionId(vote.getVotingSessionId());


        return voteDto;
    }

    public static VoteDtoWithId maptoVoteDtoWithId(Vote vote, VoteDtoWithId voteDtoWithId){

     voteDtoWithId.setVoteId(vote.getId());

     //for the dto i'll set it where this function is used

        return voteDtoWithId;
    }

}
