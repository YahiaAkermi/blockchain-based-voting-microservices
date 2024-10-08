package com.yahia.vmms.controller;


import com.yahia.vmms.constants.CondidateConstants;
import com.yahia.vmms.dto.CondidateDto;
import com.yahia.vmms.dto.CondidateDtoWithId;
import com.yahia.vmms.dto.responseStructureDTOs.ResponseDto;
import com.yahia.vmms.service.ICondidatService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/condidate")
@AllArgsConstructor
@Validated
public class CondidateController {

    private ICondidatService  iCondidatService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCondidate(@Valid @RequestBody CondidateDto condidateDto){

        iCondidatService.createCondidate(condidateDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(CondidateConstants.STATUS_201,CondidateConstants.MESSAGE_201));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CondidateDtoWithId>> getAllCondidates() {
        List<CondidateDtoWithId> condidates = iCondidatService.fetchAllCondidates();
        return ResponseEntity.ok(condidates);
    }

    @GetMapping("/fetch-by-id")
    public ResponseEntity<CondidateDtoWithId> getCondidateById(
           @NotNull(message = "condidateId should not be null") @RequestParam Long candidateId) {
        CondidateDtoWithId condidate = iCondidatService.fetchCondidateById(candidateId);
        return ResponseEntity.ok(condidate);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCondidate(
           @NotNull(message = "condidateId should not be empty") @RequestParam Long candidateId,
           @Valid @RequestBody CondidateDto condidateDto) {
        iCondidatService.updateCondidate(candidateId, condidateDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(CondidateConstants.STATUS_200, CondidateConstants.MESSAGE_200));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCondidate(
            @NotNull(message = "condidateId should not be empty") @RequestParam Long candidateId) {
        iCondidatService.deleteCondidate(candidateId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(CondidateConstants.STATUS_200, CondidateConstants.MESSAGE_200));
    }

}
