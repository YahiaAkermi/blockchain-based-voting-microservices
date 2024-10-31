package com.yahia.vmms.mapper;

import com.yahia.vmms.dto.VotingSessionDto;
import com.yahia.vmms.dto.VotingSessionDtoWithId;
import com.yahia.vmms.dto.VotingSessionDtoWithIdAndCondidates;
import com.yahia.vmms.entity.VotingSessions;
import com.yahia.vmms.entity.enums.Visibility;

public class VotingSessionMapper {

    public static  VotingSessions mapToVotingSession(VotingSessionDto votingSessionDto,VotingSessions votingSession){
        votingSession.setSessionAdminId(votingSessionDto.getSessionAdminId());
        votingSession.setTitle(votingSessionDto.getTitle());
        votingSession.setStartDate(votingSessionDto.getStartDate());
        votingSession.setEndDate(votingSessionDto.getEndDate());
        votingSession.setVotingStatus(votingSessionDto.getVotingStatus());
        votingSession.setVisibility(votingSessionDto.getVisibility());
        votingSession.setAllowedRegions(votingSessionDto.getAllowedRegions());

        return  votingSession;
    }

    public static  VotingSessionDto mapToVotingSessionDto(VotingSessions votingSession,VotingSessionDto votingSessionDto){
        votingSessionDto.setSessionAdminId(votingSession.getSessionAdminId());
        votingSessionDto.setTitle(votingSession.getTitle());
        votingSessionDto.setStartDate(votingSession.getStartDate());
        votingSessionDto.setEndDate(votingSession.getEndDate());
        votingSessionDto.setVotingStatus(votingSession.getVotingStatus());
        votingSessionDto.setVisibility(votingSession.getVisibility());
        votingSessionDto.setAllowedRegions(votingSession.getAllowedRegions());

        return  votingSessionDto;
    }

    public static VotingSessionDtoWithId mapToVotingSessionWithId(VotingSessions votingSession,VotingSessionDtoWithId votingSessionDtoWithId){

        votingSessionDtoWithId.setVotingSessionId(votingSession.getVsId());

        //for votingSessionDto I need to set it where I use this map function

        return votingSessionDtoWithId;

    }

    public static VotingSessions mapFromDtoWithIdToVotingSession(VotingSessionDtoWithId votingSessionDtoWithId,VotingSessions votingSession){

        //that's what can the session admin modify
        votingSession.setStartDate(votingSessionDtoWithId.getVotingSessionDto().getStartDate());
        votingSession.setEndDate(votingSessionDtoWithId.getVotingSessionDto().getEndDate());
        votingSession.setVotingStatus(votingSessionDtoWithId.getVotingSessionDto().getVotingStatus());
        votingSession.setVisibility(votingSessionDtoWithId.getVotingSessionDto().getVisibility());


        if(votingSessionDtoWithId.getVotingSessionDto().getVisibility().compareTo(Visibility.RESTRICTED) == 0){
            votingSession.setAllowedRegions(votingSessionDtoWithId.getVotingSessionDto().getAllowedRegions());
        }else {
            votingSession.setAllowedRegions(null);
        }


        //for votingSessionDto I need to set it where I use this map function

        return votingSession;

    }

    public static VotingSessionDtoWithIdAndCondidates mapToVotingSessionDtoWithIdAndCondidates(VotingSessions votingSessions,VotingSessionDtoWithIdAndCondidates votingSessionDtoWithIdAndCondidates){

        votingSessionDtoWithIdAndCondidates.setVotingSessionId(votingSessions.getVsId());
        votingSessionDtoWithIdAndCondidates.setVotingStatus(votingSessions.getVotingStatus());
        votingSessionDtoWithIdAndCondidates.setStartDate(votingSessions.getStartDate());
        votingSessionDtoWithIdAndCondidates.setEndDate(votingSessions.getEndDate());

        return votingSessionDtoWithIdAndCondidates;
    }



}
