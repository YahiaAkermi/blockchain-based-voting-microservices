package com.yahia.vmms.service;

import com.yahia.vmms.dto.CondidateDto;
import com.yahia.vmms.dto.SessionApplicationDto;
import com.yahia.vmms.dto.SessionApplicationDtoWithSessionDetails;
import com.yahia.vmms.entity.SessionApplication;
import com.yahia.vmms.entity.compositeIDs.ApplicationID;

import java.util.ArrayList;

public interface ISessionApplicationService {

    /**
     * This method will create an application to a voting session
     *
     * @param sessionApplicationDto - SessionApplicationDto object
     */
    void createApplication(SessionApplicationDto sessionApplicationDto);


    /**
     * This method will retrieve a particular voting session application
     * @param condidateId - Long
     * @param sessionId - Long
     */
    SessionApplicationDtoWithSessionDetails fetchByApplicationID(Long condidateId,Long sessionId);

    /**
     * This method will retrieve all applications of a particular candidate
     * @param email - String
     */
    ArrayList<SessionApplicationDtoWithSessionDetails> fetchAllAppsByCandidate(String email);

    /**
     * This method will filter candidate applications by party name
     * @param email - String
     * @param partyName - String
     */
    ArrayList<SessionApplicationDtoWithSessionDetails> filterApplicationsByParty(String email,String partyName);
}
