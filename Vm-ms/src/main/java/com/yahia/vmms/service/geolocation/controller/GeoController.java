package com.yahia.vmms.service.geolocation.controller;

import com.yahia.vmms.service.geolocation.dto.GeoResponse;
import com.yahia.vmms.service.geolocation.service.GeoLocationService;
import com.yahia.vmms.service.impl.VotingSessionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController  @AllArgsConstructor
public class GeoController {

    private final VotingSessionServiceImpl votingSessionService;



    @GetMapping("/fetch-region")
    public ResponseEntity<GeoResponse> fetchRegionByIp(@RequestParam String ip) {
        GeoResponse response = votingSessionService.getRegionByAPI(ip); // Call the service
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build(); // Handle the case when the response is null
        }
    }
}

