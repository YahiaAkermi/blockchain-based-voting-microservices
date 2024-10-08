package com.yahia.vmms.mapper;

import com.yahia.vmms.dto.SessionApplicationDto;
import com.yahia.vmms.dto.SessionApplicationDtoWithSessionDetails;
import com.yahia.vmms.entity.Condidate;
import com.yahia.vmms.entity.SessionApplication;
import com.yahia.vmms.entity.VotingSessions;
import com.yahia.vmms.entity.compositeIDs.ApplicationID;

public class SessionApplicationMapper {

    public static SessionApplication mapToSessionApplication(SessionApplicationDto sessionApplicationDto, SessionApplication sessionApplication){



        // Set other fields
        sessionApplication.setParty(sessionApplicationDto.getParty());
        sessionApplication.setProgrammeFileUrl(sessionApplicationDto.getProgrammeFileUrl());
        sessionApplication.setApplicationStatus(sessionApplicationDto.getApplicationStatus());

        return sessionApplication;
    }

    public static SessionApplicationDtoWithSessionDetails mapToSessionApplicationDtoWithSessionDetails(SessionApplication sessionApplication,SessionApplicationDtoWithSessionDetails sessionApplicationDtoWithSessionDetails){

        sessionApplicationDtoWithSessionDetails.setApplicationID(sessionApplication.getApplicationID());
        sessionApplicationDtoWithSessionDetails.setApplicationStatus(sessionApplication.getApplicationStatus());
        sessionApplicationDtoWithSessionDetails.setParty(sessionApplication.getParty());
        sessionApplicationDtoWithSessionDetails.setProgrammeFileUrl(sessionApplication.getProgrammeFileUrl());

        //for  Voting session dto i'll set it where shall I use this function

        return sessionApplicationDtoWithSessionDetails;
    }

    //with this mapping function u can only change party and programme because i'll use it for update
    public static SessionApplication mapFromApplicationWithDetailsToApplication(SessionApplicationDtoWithSessionDetails sessionApplicationDtoWithSessionDetails,SessionApplication sessionApplication){
        sessionApplication.setParty(sessionApplicationDtoWithSessionDetails.getParty());
        sessionApplication.setProgrammeFileUrl(sessionApplicationDtoWithSessionDetails.getProgrammeFileUrl());
        return sessionApplication;
    }

}
