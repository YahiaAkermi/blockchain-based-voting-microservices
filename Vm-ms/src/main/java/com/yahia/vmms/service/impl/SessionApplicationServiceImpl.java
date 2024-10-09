package com.yahia.vmms.service.impl;

import com.yahia.vmms.dto.SessionApplicationDto;
import com.yahia.vmms.dto.SessionApplicationDtoWithSessionDetails;
import com.yahia.vmms.dto.VotingSessionDto;
import com.yahia.vmms.entity.Condidate;
import com.yahia.vmms.entity.SessionApplication;
import com.yahia.vmms.entity.VotingSessions;
import com.yahia.vmms.entity.compositeIDs.ApplicationID;
import com.yahia.vmms.exception.ResourceAlreadyExists;
import com.yahia.vmms.exception.RessourceNotFoundException;
import com.yahia.vmms.mapper.SessionApplicationMapper;
import com.yahia.vmms.mapper.VotingSessionMapper;
import com.yahia.vmms.repository.CondidateRepository;
import com.yahia.vmms.repository.SessionApplicationRepository;
import com.yahia.vmms.repository.VotingSessionsRepository;
import com.yahia.vmms.service.ISessionApplicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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
    public void createApplication(SessionApplicationDto sessionApplicationDto) {

        //check if both users and session exists
        Condidate retrievedCondidate=condidateRepository.findByEmail(sessionApplicationDto.getEmail())
                .orElseThrow( () ->  new RessourceNotFoundException("Candidate","email", sessionApplicationDto.getEmail() ));

        VotingSessions retrievedVotingSession=votingSessionsRepository.findById(sessionApplicationDto.getVotingSessionId())
                .orElseThrow( () ->  new RessourceNotFoundException("Voting Session","session ID", sessionApplicationDto.getVotingSessionId().toString() ));

        //1.buid application id to search for the application if it EXISTS also i'll be using it bellow to save the application to DB
        ApplicationID applicationID=new ApplicationID();
        applicationID.setCondidateId(retrievedCondidate.getCondidateId());
        applicationID.setVotingSessionId(retrievedVotingSession.getVsId());


        //2.check if the user already applied to this session
        boolean isApplicationFound=sessionApplicationRepository.existsByApplicationID(applicationID);

        if (isApplicationFound){
         throw  new ResourceAlreadyExists("Voting Session Application",applicationID.toString());
        }


        //mapping to voting session, so I can store in the db
        SessionApplication sessionApplication= SessionApplicationMapper.mapToSessionApplication(sessionApplicationDto,new SessionApplication());

        //setting both foreign keys
        sessionApplication.setCondidate(retrievedCondidate);
        sessionApplication.setVotingSession(retrievedVotingSession);




        // Set the composite key in the sessionApplication entity
        sessionApplication.setApplicationID(applicationID);

        //eventually persisting the application into the db
        sessionApplicationRepository.save(sessionApplication);

    }

    /**
     * This method will retrieve a particular voting session application
     * @param condidateId - String
     * @param sessionId - String
     */
    @Override
    public SessionApplicationDtoWithSessionDetails fetchByApplicationID(Long condidateId,Long sessionId) {

        //build application id to search for the requested application
        ApplicationID applicationID=new ApplicationID();
        applicationID.setCondidateId(condidateId);
        applicationID.setVotingSessionId(sessionId);

        //check if the application exists
        SessionApplication retrievedApplication=sessionApplicationRepository.findById(applicationID)
                .orElseThrow(()-> new RessourceNotFoundException("Voting Session Application ","application ID",applicationID.toString() ));

        //map it to dto
        SessionApplicationDtoWithSessionDetails sessionApplicationDtoWithSessionDetails=SessionApplicationMapper.mapToSessionApplicationDtoWithSessionDetails(retrievedApplication,new SessionApplicationDtoWithSessionDetails());

        //get the voting session details so user can preview it
        VotingSessions retrievedVotingSession=votingSessionsRepository.findById(sessionId)
                .orElseThrow(()-> new RessourceNotFoundException("Voting Session","voting session ID",applicationID.getVotingSessionId().toString()));

        //map voting session to dto
        VotingSessionDto votingSessionDto= VotingSessionMapper.mapToVotingSessionDto(retrievedVotingSession,new VotingSessionDto());

        //now setting voting setting details in application dto
        sessionApplicationDtoWithSessionDetails.setVotingSessionDto(votingSessionDto);


        return sessionApplicationDtoWithSessionDetails;
    }

    /**
     * This method will retrieve all applications of a particular candidate
     *
     * @param email - String
     */
    @Override
    public ArrayList<SessionApplicationDtoWithSessionDetails> fetchAllAppsByCandidate(String email) {

        //check if the candidate already exists
        Condidate retrievedCondidate=condidateRepository.findByEmail(email).orElseThrow(
                ()->new RessourceNotFoundException("Candidate","email",email)
        );

        //get all applications of the candidate
        Collection<SessionApplication> listApplication= sessionApplicationRepository.findByApplicationID_CondidateId(retrievedCondidate.getCondidateId());


        //conversion to list of dtos with details post that returning them
        return convertFromApplicationsToDtosWithDetails(listApplication);
    }

    /**
     * This method will filter candidate's applications by party name
     * @param email     - String
     * @param partyName - String
     */
    @Override
    public ArrayList<SessionApplicationDtoWithSessionDetails> filterApplicationsByParty(String email, String partyName) {

        //check if the candidate already exists
        Condidate retrievedCondidate=condidateRepository.findByEmail(email).orElseThrow(
                ()->new RessourceNotFoundException("Candidate","email",email)
        );

        //get all applications of the candidate
        Collection<SessionApplication> listApplication= sessionApplicationRepository.findByApplicationID_CondidateIdAndPartyContainingIgnoreCase(retrievedCondidate.getCondidateId(),partyName);

        //conversion to list of dtos with details post that returning them
        return convertFromApplicationsToDtosWithDetails(listApplication);
    }

    /**
     * This method will update a voting session application
     *
     * @param sessionApplicationDtoWithSessionDetails - SessionApplicationDtoWithSessionDetails  object
     */
    @Override
    public boolean updateSessionApplication(SessionApplicationDtoWithSessionDetails sessionApplicationDtoWithSessionDetails ) {

        //check if the application exists
        SessionApplication retrievedApplication= sessionApplicationRepository.findById(sessionApplicationDtoWithSessionDetails.getApplicationID())
                .orElseThrow(()-> new RessourceNotFoundException("Voting Session Application","application ID",sessionApplicationDtoWithSessionDetails.getApplicationID().toString()));


        //u can solely change party and the submitted election programme
        SessionApplication updatedAppication=SessionApplicationMapper.mapFromApplicationWithDetailsToApplication(sessionApplicationDtoWithSessionDetails,retrievedApplication);

        //save the changes to DB
        sessionApplicationRepository.save(updatedAppication);

        return true;
    }

    /**
     * This method will delete a voting session application
     *
     * @param condidateId - Long
     * @param sessionId   - Long
     */
    @Override
    public boolean deleteSessionApplication(Long condidateId, Long sessionId) {

        //construct the applicationId
        ApplicationID applicationID=new ApplicationID();
        applicationID.setCondidateId(condidateId);
        applicationID.setVotingSessionId(sessionId);

        //check if the application exists
        SessionApplication retrievedApplication= sessionApplicationRepository.findById(applicationID)
                .orElseThrow(()-> new RessourceNotFoundException("Voting Session Application","application ID",applicationID.toString()));

        sessionApplicationRepository.deleteById(applicationID);


        return true;
    }


    public ArrayList<SessionApplicationDtoWithSessionDetails> convertFromApplicationsToDtosWithDetails(Collection<SessionApplication> listApplication){

        ArrayList<SessionApplicationDtoWithSessionDetails> listApplicationWithDetails=listApplication
                .stream().map(
                        application ->{
                            SessionApplicationDtoWithSessionDetails sessionApplicationDtoWithSessionDetails=SessionApplicationMapper.mapToSessionApplicationDtoWithSessionDetails(application,new SessionApplicationDtoWithSessionDetails());

                            //get the voting session details so user can preview it
                            VotingSessions retrievedVotingSession=votingSessionsRepository.findById(application.getApplicationID().getVotingSessionId())
                                    .orElseThrow(()-> new RessourceNotFoundException("Voting Session","voting session ID",application.getApplicationID().getVotingSessionId().toString()));

                            //map voting session to dto
                            VotingSessionDto votingSessionDto= VotingSessionMapper.mapToVotingSessionDto(retrievedVotingSession,new VotingSessionDto());

                            //now setting voting setting details in application dto
                            sessionApplicationDtoWithSessionDetails.setVotingSessionDto(votingSessionDto);

                            return  sessionApplicationDtoWithSessionDetails;
                        }
                ).collect(Collectors.toCollection(ArrayList::new));

        return listApplicationWithDetails;
    }
}




