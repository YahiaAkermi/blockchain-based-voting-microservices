package com.yahia.votingms.service.client;

import com.yahia.votingms.controller.VoteController;
import com.yahia.votingms.dto.clientDtos.VotingSessionDtoWithId;
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

@Component
public class VmMsFallback implements VmMsFeignClient{

    private static final Logger logger = LoggerFactory.getLogger(VmMsFallback.class) ;


    @Autowired
    private VsDetailsService vsDetailsService;

    @Override
    public ResponseEntity<VotingSessionDtoWithId> fetchVotingSession(String correlationId, Long votingSessionId) {

        VsDetails retrievedVsDetails= vsDetailsService.getVsDetails(votingSessionId).orElseThrow(
                ()-> new ResourceNotFoundException("Voting session details not found in the cache also please wait unitll Voting Managment service is available")
        );

        VotingSessionDtoWithId votingSessionDtoWithId= CacheMapper.mapToVotingSessionDtoWithId(retrievedVsDetails,new VotingSessionDtoWithId());

        logger.warn("-- this is cached data "+votingSessionDtoWithId.toString());

        return  ResponseEntity.status(HttpStatus.OK).body(votingSessionDtoWithId);
    }
}
