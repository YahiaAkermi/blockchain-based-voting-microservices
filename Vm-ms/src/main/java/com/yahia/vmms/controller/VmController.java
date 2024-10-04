package com.yahia.vmms.controller;


import com.yahia.vmms.constants.VotingSessionConstants;
import com.yahia.vmms.dto.VotingSessionDto;
import com.yahia.vmms.dto.VotingSessionDtoWithId;
import com.yahia.vmms.dto.responseStructureDTOs.ResponseDto;
import com.yahia.vmms.entity.enums.Visibility;
import com.yahia.vmms.entity.enums.VotingStatus;
import com.yahia.vmms.service.IVotingSessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/voting-managment")
@AllArgsConstructor
public class VmController {

    private IVotingSessionService iVotingSessionService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createVotingSession(@RequestBody VotingSessionDto votingSessionDto){

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
    public ResponseEntity<ArrayList< VotingSessionDtoWithId>> filterByTitle(@RequestParam Long adminSessionId, @RequestParam String title){

        ArrayList< VotingSessionDtoWithId>  filteredVotingSessionBytitle=  iVotingSessionService.filterVotingSessionBytitle(adminSessionId,title);

        return ResponseEntity.status(HttpStatus.OK)
                .body(filteredVotingSessionBytitle);
    }


    @GetMapping("/fetch-by-status")
    public ResponseEntity<ArrayList< VotingSessionDtoWithId>> filterByVotingSessionStatus(@RequestParam VotingStatus sessionStatus, HttpServletRequest request){

        String clientIP = request.getHeader("X-Forwarded-For");
        if (clientIP == null || clientIP.isEmpty()) {
            clientIP = request.getRemoteAddr();
        }

       ArrayList<VotingSessionDtoWithId>  votingSessionfilteredByStatus= iVotingSessionService.filterVotingSessionByStatus(sessionStatus,clientIP);

        return ResponseEntity.status(HttpStatus.OK).body(votingSessionfilteredByStatus);
    }

    @GetMapping("/fetch-by-id")
    public ResponseEntity< VotingSessionDtoWithId> fetchById(@RequestParam Long votingSessionId){


        VotingSessionDtoWithId votingSessionDtoWithId=iVotingSessionService.fetchVotingSessionById(votingSessionId);

        return ResponseEntity.status(HttpStatus.OK).body(votingSessionDtoWithId);
    }
}
