package com.yahia.vmms.service;

import com.yahia.vmms.dto.VotingSessionDto;
import com.yahia.vmms.dto.VotingSessionDtoWithId;
import com.yahia.vmms.entity.VotingSessions;
import com.yahia.vmms.entity.enums.Visibility;
import com.yahia.vmms.entity.enums.VotingStatus;

import java.util.ArrayList;

public interface IVotingSessionService {

    /**
     *this method will create voting session
     *@param votingSessionDto -  VotingSessionDto  object
     */
    void createVotingSession(VotingSessionDto votingSessionDto);

    /**
     *this method will retrieve all authorized voting sessions
     * @param clientIpAddress -  VotingSessionDto  object
     */
    ArrayList<VotingSessionDtoWithId> fetchAuthorizedVotingSession(String clientIpAddress);


    /**
     *this method will filter voting sessions for a particular Admin
     * @param sessionAdminId -  Long
     * @param searchTerm -  String
     */
    ArrayList<VotingSessionDtoWithId> filterVotingSessionBytitle(Long sessionAdminId, String searchTerm);

    /**
     *this method will filter voting sessions by status for only public or authorized regions
     * @param sessionStatus -  String
     *@param clientIp -String
     */
    ArrayList<VotingSessionDtoWithId> filterVotingSessionByStatus(VotingStatus sessionStatus, String clientIp);
}
