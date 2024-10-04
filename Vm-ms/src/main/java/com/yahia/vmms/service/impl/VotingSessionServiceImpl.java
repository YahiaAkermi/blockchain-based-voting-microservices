package com.yahia.vmms.service.impl;

import com.yahia.vmms.dto.VotingSessionDto;

import com.yahia.vmms.dto.VotingSessionDtoWithId;
import com.yahia.vmms.entity.VotingSessions;
import com.yahia.vmms.entity.enums.Visibility;
import com.yahia.vmms.entity.enums.VotingStatus;
import com.yahia.vmms.exception.DateTimeIncohrentException;
import com.yahia.vmms.exception.RessourceNotFoundException;
import com.yahia.vmms.exception.VotingSessionAlreadyExists;
import com.yahia.vmms.mapper.VotingSessionMapper;
import com.yahia.vmms.repository.CondidateRepository;
import com.yahia.vmms.repository.VotingSessionsRepository;
import com.yahia.vmms.service.IVotingSessionService;
import com.yahia.vmms.service.geolocation.dto.GeoResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VotingSessionServiceImpl implements IVotingSessionService {
    private static final Logger logger = LoggerFactory.getLogger(IVotingSessionService.class);

    //injecting our repos along with restTemplate to make a call to another 3rd party API
    public VotingSessionServiceImpl(VotingSessionsRepository votingSessionsRepository
            ,CondidateRepository condidateRepository,RestTemplate restTemplate) {
        this.restTemplate=restTemplate;
        this.votingSessionsRepository=votingSessionsRepository;
        this.condidateRepository=condidateRepository;

    }

    private final VotingSessionsRepository votingSessionsRepository;
    private final CondidateRepository condidateRepository;

    @Value("${geolocation.api.url}") //you find it inside application.properties
    private String geolocationAPI;

    private final RestTemplate restTemplate;


    //this is to get the country after making api call
    public GeoResponse getRegionByAPI(String ipAddress){
        String apiUrl=String.format("%s/%s",geolocationAPI,ipAddress);
        return restTemplate.getForObject(apiUrl, GeoResponse.class);
    }

    /**
     * this method will create voting session
     *
     * @param votingSessionDto -  VotingSessionDto  object
     */
    @Override
    public void createVotingSession(VotingSessionDto votingSessionDto) {

        //first I am going to check if the voting session already exists
        if(votingSessionsRepository.existsByTitleIgnoreCase(votingSessionDto.getTitle())){
         throw  new VotingSessionAlreadyExists("Voting session already exists with the given title : "+votingSessionDto.getTitle());
        }

        //map voting session dto to voting session ,so we can store it in the db
        VotingSessions votingSession= VotingSessionMapper.mapToVotingSession(votingSessionDto,new VotingSessions());

        //first i'm gonna delay the session start Date with 1MIN to deal the network congestion then i'm gonna add it at the end
        votingSession.setStartDate(votingSession.getStartDate().plusMinutes(1));

        //making sure that the voting session start date is after request being fired
        if(votingSession.getStartDate().isBefore(LocalDateTime.now())){
            throw  new DateTimeIncohrentException("session start date should be after session creation request");
        }


        //we need first to check in the start date is not a day before the request was first also the end date
        //also bear mind that the end date is not inferior the start
        LocalDateTime sessionStartDate=votingSession.getStartDate();
        LocalDateTime sessionEndDate=votingSession.getEndDate();

        if(sessionEndDate.isBefore(sessionStartDate)){
            throw  new DateTimeIncohrentException("session end date should not be before session start date");
        }

        //adding the 1min delay to voting session end date to be more transparent and honest
        votingSession.setEndDate(votingSession.getEndDate().plusMinutes(1));

        //finally we need to set both fields createdAt and createdBy
        votingSession.setCreatedAt(LocalDateTime.now());
        votingSession.setCreatedBy("ADMIN");

        //saving the  new voting to the db
        VotingSessions savedVotingSession =votingSessionsRepository.save(votingSession);

        //add some logic to store this action in redis cache or delegate this task to a dedicated
        // audit/log microservice



    }

    /**
     * this method will retrieve all authorized voting sessions
     *
     * @param clientIpAddress -  VotingSessionDto  object
     */
    @Override
    public ArrayList<VotingSessionDtoWithId> fetchAuthorizedVotingSession(String clientIpAddress) {

        logger.warn("client ip address ----> " + clientIpAddress);

        //here i'm simulating having ip address since i'm in localhost and testing so it's not convenient to use loopback address for
        //testing this geolocation functionality
        GeoResponse location = getRegionByAPI("24.48.0.1");

        logger.info("country :{ " + location.getCountryCode() + " , " + location.getCity() + " }");

        ArrayList<VotingSessions> listAuthorizedVotingSessions = (ArrayList<VotingSessions>)
                votingSessionsRepository.findAllByAllowedRegionsContainingOrVisibilityEquals(location.getCountryCode(), Visibility.PUBLIC);

       return listAuthorizedVotingSessions.stream().map(votingSessions -> {

            //first i'm gonna map from voting session to dto with id
            VotingSessionDtoWithId votingSessionDtoWithId =
                    VotingSessionMapper.mapToVotingSessionWithId(votingSessions, new VotingSessionDtoWithId());

            //then i will set the dto after mapping it from voting session
            votingSessionDtoWithId.setVotingSessionDto(VotingSessionMapper.mapToVotingSessionDto(votingSessions, new VotingSessionDto()));

            return votingSessionDtoWithId;
        }).collect(Collectors.toCollection(ArrayList::new));


    }



    /**
     *this method will filter voting sessions for a particular Admin
     * @param sessionAdminId -  Long
     * @param searchTerm -  String
     */
    @Override
    public ArrayList<VotingSessionDtoWithId> filterVotingSessionBytitle(Long sessionAdminId, String searchTerm) {

        //2.retrieve the voting sessions for that particular admin filtered by searchTerm
        Collection<VotingSessions> listRetrievedVotingSessions=votingSessionsRepository
                .findAllBySessionAdminIdAndTitleContainingIgnoreCase(sessionAdminId, searchTerm);

        //3.turning them to dto's so the user can see them but dto with id
       return listRetrievedVotingSessions.stream().map(votingSessions -> {

            //converting to dto
            VotingSessionDto votingSessionDto=  VotingSessionMapper.mapToVotingSessionDto(votingSessions,new VotingSessionDto());

          //converting to dto with id
            VotingSessionDtoWithId votingSessionDtoWithId=VotingSessionMapper.mapToVotingSessionWithId(votingSessions,new VotingSessionDtoWithId());
          //finally i need set the dto to dto with id
          votingSessionDtoWithId.setVotingSessionDto(votingSessionDto);

          return votingSessionDtoWithId;
        }).collect(Collectors.toCollection(ArrayList::new));

    }

    /**
     * this method will filter voting sessions by status for only public or authorized regions
     * @param sessionStatus -  String
     * @param clientIpAddress -String
     */
    @Override
    public ArrayList<VotingSessionDtoWithId> filterVotingSessionByStatus(VotingStatus sessionStatus, String clientIpAddress) {

        logger.warn("client ip address ----> " + clientIpAddress);

        //here i'm simulating having ip address since i'm in localhost and testing so it's not convenient to use loopback address for
        //testing this geolocation functionality
        GeoResponse location = getRegionByAPI("24.48.0.1");

        logger.info("country :{ " + location.getCountryCode() + " , " + location.getCity() + " }");

        ArrayList<VotingSessions> listAuthorizedVotingSessionsfilteredByStatus = (ArrayList<VotingSessions>)
                votingSessionsRepository.findAllByVotingStatusEqualsAndAllowedRegionsContainingOrVisibilityEquals(sessionStatus,location.getCountryCode(), Visibility.PUBLIC);




        return listAuthorizedVotingSessionsfilteredByStatus.stream().map(votingSessions -> {
            VotingSessionDtoWithId votingSessionDtoWithId=VotingSessionMapper.mapToVotingSessionWithId(votingSessions,new VotingSessionDtoWithId());

            votingSessionDtoWithId.setVotingSessionDto(VotingSessionMapper.mapToVotingSessionDto(votingSessions,new VotingSessionDto()));

            return votingSessionDtoWithId;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * this method will retrieve a particular voting session
     *
     * @param votingSessionId -  Long
     */
    @Override
    public VotingSessionDtoWithId fetchVotingSessionById(Long votingSessionId) {

        //we're retrieving the voting session if it doesn't exist we return resource not found exception
        VotingSessions retrievedVotingSession= votingSessionsRepository.findById(votingSessionId)
                .orElseThrow(() -> new RessourceNotFoundException("Voting Session", "voting session ID", votingSessionId.toString()));

        //transforming it to dto with id
        VotingSessionDtoWithId votingSessionDtoWithId=VotingSessionMapper.mapToVotingSessionWithId(retrievedVotingSession,new VotingSessionDtoWithId());
        votingSessionDtoWithId.setVotingSessionDto(VotingSessionMapper.mapToVotingSessionDto(retrievedVotingSession,new VotingSessionDto()));

        return votingSessionDtoWithId;
    }
}
