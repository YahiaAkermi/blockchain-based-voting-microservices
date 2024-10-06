package com.yahia.vmms.mapper;

import com.yahia.vmms.dto.CondidateDto;
import com.yahia.vmms.entity.Condidate;

public class CondidateMapper {

    public static Condidate mapToCondidate(CondidateDto condidateDto,Condidate condidate){
        condidate.setName(condidateDto.getName());
        condidate.setEmail(condidateDto.getEmail());
        condidate.setDateOfBirth(condidateDto.getDateOfBirth());


        return condidate;
    }

    public static CondidateDto mapToCondidateDto(Condidate condidate,CondidateDto condidateDto){
        condidateDto.setName(condidate.getName());
        condidateDto.setEmail(condidate.getEmail());
        condidateDto.setDateOfBirth(condidate.getDateOfBirth());

        return condidateDto;
    }
}
