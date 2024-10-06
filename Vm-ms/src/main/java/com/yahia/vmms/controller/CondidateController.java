package com.yahia.vmms.controller;


import com.yahia.vmms.constants.CondidateConstants;
import com.yahia.vmms.dto.CondidateDto;
import com.yahia.vmms.dto.responseStructureDTOs.ResponseDto;
import com.yahia.vmms.service.ICondidatService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/condidate")
@AllArgsConstructor
public class CondidateController {

    private ICondidatService  iCondidatService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCondidate(@RequestBody CondidateDto condidateDto){

        iCondidatService.createCondidate(condidateDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(CondidateConstants.STATUS_201,CondidateConstants.MESSAGE_201));
    }

}
