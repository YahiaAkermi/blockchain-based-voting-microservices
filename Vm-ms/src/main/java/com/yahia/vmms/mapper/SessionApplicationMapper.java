package com.yahia.vmms.mapper;

import com.yahia.vmms.dto.SessionApplicationDto;
import com.yahia.vmms.dto.SessionApplicationDtoWithSessionDetails;
import com.yahia.vmms.entity.Condidate;
import com.yahia.vmms.entity.SessionApplication;
import com.yahia.vmms.entity.VotingSessions;
import com.yahia.vmms.entity.compositeIDs.ApplicationID;

public class SessionApplicationMapper {

    public static SessionApplication mapToSessionApplication(SessionApplicationDto sessionApplicationDto, SessionApplication sessionApplication, Condidate condidate, VotingSessions votingSession){

        // 1. building our composite key
        ApplicationID applicationID=new ApplicationID();

        applicationID.setCondidate(condidate);
        applicationID.setVotingSession(votingSession);


        sessionApplication.setApplicationId(applicationID);
        sessionApplication.setParty(sessionApplicationDto.getParty());
        sessionApplication.setProgrammeFileUrl(sessionApplicationDto.getProgrammeFileUrl());
        sessionApplication.setApplicationStatus(sessionApplication.getApplicationStatus());

      return  sessionApplication;
    }

    public static SessionApplicationDtoWithSessionDetails mapToSessionApplicationDtoWithSessionDetails(SessionApplication sessionApplication,SessionApplicationDtoWithSessionDetails sessionApplicationDtoWithSessionDetails){

        sessionApplicationDtoWithSessionDetails.setApplicationID(sessionApplication.getApplicationId());
        sessionApplicationDtoWithSessionDetails.setApplicationStatus(sessionApplication.getApplicationStatus());
        sessionApplicationDtoWithSessionDetails.setParty(sessionApplication.getParty());
        sessionApplicationDtoWithSessionDetails.setProgrammeFileUrl(sessionApplication.getProgrammeFileUrl());

        //for  Voting session dto i'll set it where shall I use this function

        return sessionApplicationDtoWithSessionDetails;
    }

}
