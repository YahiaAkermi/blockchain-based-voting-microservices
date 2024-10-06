package com.yahia.vmms.service.impl;

import com.yahia.vmms.dto.SessionApplicationDto;
import com.yahia.vmms.repository.CondidateRepository;
import com.yahia.vmms.repository.SessionApplicationRepository;
import com.yahia.vmms.repository.VotingSessionsRepository;
import com.yahia.vmms.service.ISessionApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class SessionApplicationServiceImpl implements ISessionApplicationService {

    private final SessionApplicationRepository sessionApplicationRepository;
    private final CondidateRepository condidateRepository;

    private final VotingSessionsRepository votingSessionsRepository;


    public SessionApplicationServiceImpl(SessionApplicationRepository sessionApplicationRepository, CondidateRepository condidateRepository, VotingSessionsRepository votingSessionsRepository){
        this.condidateRepository=condidateRepository;
        this.sessionApplicationRepository=sessionApplicationRepository;
        this.votingSessionsRepository=votingSessionsRepository;
    }


    /**
     * This method will create an application to a voting session
     *
     * @param sessionApplicationDto - SessionApplicationDto object
     */
    @Override
    public void createCondidate(SessionApplicationDto sessionApplicationDto) {

    }
}
