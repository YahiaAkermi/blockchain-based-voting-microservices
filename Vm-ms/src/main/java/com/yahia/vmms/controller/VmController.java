package com.yahia.vmms.controller;


import com.yahia.vmms.constants.VotingSessionConstants;
import com.yahia.vmms.dto.VotingSessionDto;
import com.yahia.vmms.dto.VotingSessionDtoWithId;
import com.yahia.vmms.dto.configRecord.VmContactInfoDto;
import com.yahia.vmms.dto.responseStructureDTOs.ResponseDto;
import com.yahia.vmms.entity.enums.Visibility;
import com.yahia.vmms.entity.enums.VotingStatus;
import com.yahia.vmms.service.IVotingSessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/voting-managment")
@AllArgsConstructor
@Validated
public class VmController {

    private IVotingSessionService iVotingSessionService;

    @Autowired
    private VmContactInfoDto vmContactInfoDto;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createVotingSession(@Valid @RequestBody VotingSessionDto votingSessionDto){

        iVotingSessionService.createVotingSession(votingSessionDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(VotingSessionConstants.STATUS_201,VotingSessionConstants.MESSAGE_201));

    }

    @GetMapping("/fetch-authorized-voting-sessions")
    public ResponseEntity<ArrayList< VotingSessionDtoWithId>> fetchAllVotingSession(HttpServletRequest request){

        //this is another to do it
        //String clientIP=request.getRemoteAddr();

        //i'm using the header of the request so i can easily get the ipv6 address behind loadbalancer of proxy
        String clientIP = request.getHeader("X-Forwarded-For");
        if (clientIP == null || clientIP.isEmpty()) {
            clientIP = request.getRemoteAddr();
        }

   ArrayList<VotingSessionDtoWithId> authorizedVotingSessions=   iVotingSessionService.fetchAuthorizedVotingSession(clientIP);


      return ResponseEntity.status(HttpStatus.OK)
              .body(authorizedVotingSessions);
    }

    @GetMapping("/fetch-by-title")
    public ResponseEntity<ArrayList< VotingSessionDtoWithId>> filterByTitle(
            @NotNull(message = "adminSessionId should not be empty") @RequestParam Long adminSessionId,
            @NotEmpty(message = "title should not be empty") @RequestParam String title){

        ArrayList< VotingSessionDtoWithId>  filteredVotingSessionBytitle=  iVotingSessionService.filterVotingSessionBytitle(adminSessionId,title);

        return ResponseEntity.status(HttpStatus.OK)
                .body(filteredVotingSessionBytitle);
    }


    @GetMapping("/fetch-by-status")
    public ResponseEntity<ArrayList< VotingSessionDtoWithId>> filterByVotingSessionStatus(
             @RequestParam VotingStatus sessionStatus
            , HttpServletRequest request){

        String clientIP = request.getHeader("X-Forwarded-For");
        if (clientIP == null || clientIP.isEmpty()) {
            clientIP = request.getRemoteAddr();
        }

       ArrayList<VotingSessionDtoWithId>  votingSessionfilteredByStatus= iVotingSessionService.filterVotingSessionByStatus(sessionStatus,clientIP);

        return ResponseEntity.status(HttpStatus.OK).body(votingSessionfilteredByStatus);
    }

    @GetMapping("/fetch-by-id")
    public ResponseEntity< VotingSessionDtoWithId> fetchById(
            @NotEmpty(message = "votingSessionId should not be empty") @RequestParam Long votingSessionId){


        VotingSessionDtoWithId votingSessionDtoWithId=iVotingSessionService.fetchVotingSessionById(votingSessionId);

        return ResponseEntity.status(HttpStatus.OK).body(votingSessionDtoWithId);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateVotingSession(@Valid @RequestBody VotingSessionDtoWithId votingSessionDtoWithId){

         boolean isUpdated= iVotingSessionService.updateVotingSession(votingSessionDtoWithId);

         if (isUpdated){
             return  ResponseEntity.status(HttpStatus.OK)
                     .body(new ResponseDto(VotingSessionConstants.STATUS_200,VotingSessionConstants.MESSAGE_200));
         }else{
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body(new ResponseDto(VotingSessionConstants.STATUS_417,VotingSessionConstants.MESSAGE_417_UPDATE));
         }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteVotingSession(
            @NotNull(message = "sessionId should not be null")
            @RequestParam Long sessionId){

        boolean isDeleted= iVotingSessionService.deleteVotingSession(sessionId);

        if (isDeleted){
            return  ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(VotingSessionConstants.STATUS_200,VotingSessionConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(VotingSessionConstants.STATUS_417,VotingSessionConstants.MESSAGE_417_DELETE));
        }

    }

    @GetMapping("/contact-info")
    public ResponseEntity<VmContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vmContactInfoDto);
    }


}
