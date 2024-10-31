package com.yahia.votingms.service.client;

import com.yahia.votingms.dto.clientDtos.VotingSessionDtoWithId;
import com.yahia.votingms.dto.clientDtos.VotingSessionDtoWithIdAndCondidates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "vm-ms",fallback = VmMsFallback.class)
public interface VmMsFeignClient {

    @GetMapping(value = "/voting-managment/fetch-by-id2",consumes = "application/json")
    public ResponseEntity<VotingSessionDtoWithIdAndCondidates> fetchVotingSession(@RequestHeader("yahiaORG-correlation-id") String correlationId, @RequestParam Long votingSessionId);
}
