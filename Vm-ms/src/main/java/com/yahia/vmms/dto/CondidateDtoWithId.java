package com.yahia.vmms.dto;

import com.yahia.vmms.mapper.CondidateMapper;
import lombok.Data;

@Data
public class CondidateDtoWithId {

    private Long CondidateId;

    private CondidateDto condidateDto;
}
