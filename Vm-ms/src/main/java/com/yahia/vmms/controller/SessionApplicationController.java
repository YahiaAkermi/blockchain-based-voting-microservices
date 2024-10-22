package com.yahia.vmms.controller;

import com.yahia.vmms.constants.SessionApplicationConstants;
import com.yahia.vmms.dto.SessionApplicationDto;
import com.yahia.vmms.dto.SessionApplicationDtoWithSessionDetails;
import com.yahia.vmms.dto.responseStructureDTOs.ResponseDto;
import com.yahia.vmms.entity.SessionApplication;
import com.yahia.vmms.entity.compositeIDs.ApplicationID;
import com.yahia.vmms.repository.SessionApplicationRepository;
import com.yahia.vmms.service.ISessionApplicationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController @AllArgsConstructor
@RequestMapping("/session-application")
@Validated
public class SessionApplicationController {

    private final ISessionApplicationService iSessionApplicationService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createSessionApplication(
          @Valid @RequestBody SessionApplicationDto sessionApplicationDto){

        iSessionApplicationService.createApplication(sessionApplicationDto);

        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(SessionApplicationConstants.STATUS_201,SessionApplicationConstants.MESSAGE_201));
    }

    @GetMapping("/fetch-by-id")
    public ResponseEntity<SessionApplicationDtoWithSessionDetails> fetchByApplicationID(
            @NotNull(message = "condidateId should not be empty") @RequestParam Long condidateId,
            @NotNull(message = "sessionId should not be empty") @RequestParam Long sessionId){

        SessionApplicationDtoWithSessionDetails appSessionWithDeatails=iSessionApplicationService.fetchByApplicationID(condidateId,sessionId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(appSessionWithDeatails);

    }

    @GetMapping("/fetch-all-applications-by-candidate")
    public ResponseEntity<ArrayList< SessionApplicationDtoWithSessionDetails>> fetchAllAppsByCandidate(
            @Email(message = "email address is not valid") @RequestParam String email){

        ArrayList<SessionApplicationDtoWithSessionDetails> listApplicationsOfParticularUser=iSessionApplicationService.fetchAllAppsByCandidate(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(listApplicationsOfParticularUser);

    }

    @GetMapping("/filter-by-party")
    public ResponseEntity<ArrayList< SessionApplicationDtoWithSessionDetails>> filterApplicationsByParty(
            @Email(message = "email address is not valid") @RequestParam String email,
           @NotEmpty(message = "partyName should not be empty") @RequestParam String partyName){


        ArrayList<SessionApplicationDtoWithSessionDetails> listApplicationsOfParticularUserParty=iSessionApplicationService.filterApplicationsByParty(email,partyName);

        return ResponseEntity.status(HttpStatus.OK)
                .body(listApplicationsOfParticularUserParty);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateSessionApplication(@Valid @RequestBody SessionApplicationDtoWithSessionDetails sessionApplicationDtoWithSessionDetails){

        boolean isUpdated= iSessionApplicationService.updateSessionApplication(sessionApplicationDtoWithSessionDetails);

        if (isUpdated){

            return  ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(SessionApplicationConstants.STATUS_200,SessionApplicationConstants.MESSAGE_200));
        }else {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(SessionApplicationConstants.STATUS_417,SessionApplicationConstants.MESSAGE_417_UPDATE));
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteApplication(
            @NotNull(message = "condidateId should not be empty") @RequestParam Long condidateId,
            @NotNull(message = "sessionId should not be empty") @RequestParam Long sessionId){

        boolean isDeleted= iSessionApplicationService.deleteSessionApplication(condidateId,sessionId);

        if (isDeleted){

            return  ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(SessionApplicationConstants.STATUS_200,SessionApplicationConstants.MESSAGE_200));
        }else {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(SessionApplicationConstants.STATUS_417,SessionApplicationConstants.MESSAGE_417_DELETE));
        }
    }



}
