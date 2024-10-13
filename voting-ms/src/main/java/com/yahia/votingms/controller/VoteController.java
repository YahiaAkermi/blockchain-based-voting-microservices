package com.yahia.votingms.controller;

import com.yahia.votingms.constants.VoteConstants;
import com.yahia.votingms.dto.VoteDto;
import com.yahia.votingms.dto.responseStructureDTOs.ResponseDto;
import com.yahia.votingms.entity.Vote;
import com.yahia.votingms.service.IVoteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/vote") @AllArgsConstructor
public class VoteController {

    private final IVoteService iVoteService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createVote(@RequestBody VoteDto voteDto){

        iVoteService.createVote(voteDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(VoteConstants.STATUS_201,VoteConstants.MESSAGE_201));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Vote>> getAllVotes(@RequestParam Long bySessionId, @RequestParam Integer pageNum, @RequestParam Integer pageSize){

        Page<Vote> votesPerPageForParticularVotingSession=iVoteService.fetchAllVotesBySession(bySessionId,pageNum,pageSize);

        return ResponseEntity.status(HttpStatus.OK)
                .body(votesPerPageForParticularVotingSession);
    }


}
