package com.yahia.vmms.controller;

import com.yahia.vmms.constants.SessionApplicationConstants;
import com.yahia.vmms.dto.SessionApplicationDto;
import com.yahia.vmms.dto.SessionApplicationDtoWithSessionDetails;
import com.yahia.vmms.dto.responseStructureDTOs.ResponseDto;
import com.yahia.vmms.entity.SessionApplication;
import com.yahia.vmms.entity.compositeIDs.ApplicationID;
import com.yahia.vmms.repository.SessionApplicationRepository;
import com.yahia.vmms.service.ISessionApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController @AllArgsConstructor
@RequestMapping("/session-application")
public class SessionApplicationController {

    private final ISessionApplicationService iSessionApplicationService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createSessionApplication(@RequestBody SessionApplicationDto sessionApplicationDto){

        iSessionApplicationService.createApplication(sessionApplicationDto);

        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(SessionApplicationConstants.STATUS_201,SessionApplicationConstants.MESSAGE_201));
    }

    @GetMapping("/fetch-by-id")
    public ResponseEntity<SessionApplicationDtoWithSessionDetails> fetchByApplicationID(@RequestParam Long condidateId,@RequestParam Long sessionId){

        SessionApplicationDtoWithSessionDetails appSessionWithDeatails=iSessionApplicationService.fetchByApplicationID(condidateId,sessionId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(appSessionWithDeatails);

    }

    @GetMapping("/fetch-all-applications-by-candidate")
    public ResponseEntity<ArrayList< SessionApplicationDtoWithSessionDetails>> fetchAllAppsByCandidate(@RequestParam String email){

        ArrayList<SessionApplicationDtoWithSessionDetails> listApplicationsOfParticularUser=iSessionApplicationService.fetchAllAppsByCandidate(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(listApplicationsOfParticularUser);

    }

    @GetMapping("/filter-by-party")
    public ResponseEntity<ArrayList< SessionApplicationDtoWithSessionDetails>> filterApplicationsByParty(@RequestParam String email,@RequestParam String partyName){


        ArrayList<SessionApplicationDtoWithSessionDetails> listApplicationsOfParticularUserParty=iSessionApplicationService.filterApplicationsByParty(email,partyName);

        return ResponseEntity.status(HttpStatus.OK)
                .body(listApplicationsOfParticularUserParty);

    }




}
