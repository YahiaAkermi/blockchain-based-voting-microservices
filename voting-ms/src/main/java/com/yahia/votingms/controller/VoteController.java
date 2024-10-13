package com.yahia.votingms.controller;

import com.yahia.votingms.constants.VoteConstants;
import com.yahia.votingms.dto.VoteDto;
import com.yahia.votingms.dto.VoteDtoWithId;
import com.yahia.votingms.dto.responseStructureDTOs.ResponseDto;
import com.yahia.votingms.entity.Vote;
import com.yahia.votingms.service.IVoteService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

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
    public ResponseEntity<Page<Vote>> getAllVotesBySession(@RequestParam Long bySessionId, @RequestParam Integer pageNum, @RequestParam Integer pageSize){

        Page<Vote> votesPerPageForParticularVotingSession=iVoteService.fetchAllVotesBySession(bySessionId,pageNum,pageSize);

        return ResponseEntity.status(HttpStatus.OK)
                .body(votesPerPageForParticularVotingSession);
    }

    @GetMapping("/filter-by-condidate")
    public ResponseEntity<Page<Vote>> getAllVotesByCondidate(@RequestParam Long condidateId, @RequestParam Integer pageNum, @RequestParam Integer pageSize){


        Page<Vote> votesPerPageForParticularCandidate=iVoteService.fetchAllVotesByCandidate(condidateId,pageNum,pageSize);

        return ResponseEntity.status(HttpStatus.OK)
                .body(votesPerPageForParticularCandidate);
    }

    @GetMapping("/filter-by-voter")
    public ResponseEntity<Page<Vote>> getAllVotesByVoter(@RequestParam Long voterId, @RequestParam Integer pageNum, @RequestParam Integer pageSize){


        Page<Vote> votesPerPageForParticularVoter=iVoteService.fetchAllVotesByVoter(voterId,pageNum,pageSize);

        return ResponseEntity.status(HttpStatus.OK)
                .body(votesPerPageForParticularVoter);
    }

    @GetMapping("/fetch")
    public ResponseEntity<VoteDtoWithId> fetchVote(@RequestParam String voteId){


        VoteDtoWithId votesPerPageForParticularVoter=iVoteService.fetchVote(voteId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(votesPerPageForParticularVoter);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateVote(@RequestParam String voteId,@RequestBody VoteDto voteDto ){

        boolean isUpdated = iVoteService.updateVote(voteId,voteDto);

        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(VoteConstants.STATUS_200,VoteConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(VoteConstants.STATUS_417,VoteConstants.MESSAGE_417_UPDATE));
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteVote(@RequestParam String voteId){

        boolean isDeleted = iVoteService.deleteVote(voteId);

        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(VoteConstants.STATUS_200,VoteConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(VoteConstants.STATUS_417,VoteConstants.MESSAGE_417_DELETE));
        }

    }







}
