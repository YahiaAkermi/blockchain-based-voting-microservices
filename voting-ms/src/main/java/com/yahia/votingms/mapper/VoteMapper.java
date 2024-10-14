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

    public static Vote maptoVote2(VoteDto voteDto,Vote vote){

        // i cannot set the voterId or voting because it doesn't make any sense I'm going to use it inside updateVote
        //so i only change the candidateId
        vote.setCondidateId(voteDto.getCondidateId());
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
