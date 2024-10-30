package com.yahia.votingms.service.client.vsDetailsCache;

import com.yahia.votingms.dto.clientDtos.VotingSessionDto;
import com.yahia.votingms.dto.clientDtos.VotingSessionDtoWithId;

public  class CacheMapper {

    public static VsDetails mapToVsDetails(VotingSessionDtoWithId votingSessionDtoWithId,VsDetails vsDetails){

        vsDetails.setVotingSessionId(votingSessionDtoWithId.getVotingSessionId());
        vsDetails.setStartDate(votingSessionDtoWithId.getVotingSessionDto().getStartDate());
        vsDetails.setEndDate(votingSessionDtoWithId.getVotingSessionDto().getEndDate());
        vsDetails.setVotingStatus(votingSessionDtoWithId.getVotingSessionDto().getVotingStatus());

        return vsDetails;
    }

    public static VotingSessionDtoWithId mapToVotingSessionDtoWithId(VsDetails vsDetails,VotingSessionDtoWithId votingSessionDtoWithId){

        VotingSessionDto dto=new VotingSessionDto();
            dto.setStartDate(vsDetails.getStartDate());
            dto.setEndDate(vsDetails.getEndDate());
            dto.setVotingStatus(vsDetails.getVotingStatus());

            votingSessionDtoWithId.setVotingSessionId(vsDetails.getVotingSessionId());
            votingSessionDtoWithId.setVotingSessionDto(dto);

        return votingSessionDtoWithId;
    }

}
