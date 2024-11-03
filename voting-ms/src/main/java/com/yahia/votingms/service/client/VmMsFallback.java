package com.yahia.votingms.service.client;

import com.yahia.votingms.controller.VoteController;
import com.yahia.votingms.dto.clientDtos.VotingSessionDtoWithId;
import com.yahia.votingms.dto.clientDtos.VotingSessionDtoWithIdAndCondidates;
import com.yahia.votingms.exception.ResourceNotFoundException;
import com.yahia.votingms.service.client.vsDetailsCache.CacheMapper;
import com.yahia.votingms.service.client.vsDetailsCache.VsDetails;
import com.yahia.votingms.service.client.vsDetailsCache.VsDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisSubscribedConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class VmMsFallback implements VmMsFeignClient{

    private static final Logger logger = LoggerFactory.getLogger(VmMsFallback.class) ;


    @Autowired
    private VsDetailsService vsDetailsService;

    @Override
    public ResponseEntity<VotingSessionDtoWithIdAndCondidates> fetchVotingSession(String correlationId, Long votingSessionId) {

        VsDetails retrievedVsDetails= vsDetailsService.getVsDetails(votingSessionId).orElseThrow(
                ()-> new ResourceNotFoundException("Voting session details not found in the cache also please wait unitll Voting Managment service is available")
        );

        VotingSessionDtoWithIdAndCondidates votingSessionDtoWithIdAndCondidates= CacheMapper.mapToVotingSessionDtoWithIdAndCondidates(retrievedVsDetails,new VotingSessionDtoWithIdAndCondidates());

        if(retrievedVsDetails.getListCondidates() == null){
            throw new ResourceNotFoundException(String.format("either the voting session detail for session with id: %s hasn't been cached or the voting session has not candidates yet",votingSessionId));
        }

        // I need to transform the String to arrayList
        ArrayList<Long> listCondidates= Arrays.stream(retrievedVsDetails.getListCondidates().split(","))
                                     .map(item ->Long.parseLong(item)).collect(Collectors.toCollection(ArrayList::new));

        //now i need to set the conddidatesList in dto
        votingSessionDtoWithIdAndCondidates.setListCondidates(listCondidates);


        logger.warn("-- this is cached data "+votingSessionDtoWithIdAndCondidates.toString());

        return  ResponseEntity.status(HttpStatus.OK).body(votingSessionDtoWithIdAndCondidates);
    }
}
