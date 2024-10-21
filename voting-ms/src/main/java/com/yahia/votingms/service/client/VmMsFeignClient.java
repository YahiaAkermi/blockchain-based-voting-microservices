package com.yahia.votingms.service.client;

import com.yahia.votingms.dto.clientDtos.VotingSessionDtoWithId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("vm-ms")
public interface VmMsFeignClient {

    @GetMapping(value = "/voting-managment/fetch-by-id",consumes = "application/json")
    public ResponseEntity<VotingSessionDtoWithId> fetchVotingSession(@RequestParam Long votingSessionId);
}
