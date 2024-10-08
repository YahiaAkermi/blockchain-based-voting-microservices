package com.yahia.vmms.dto;

import com.yahia.vmms.mapper.CondidateMapper;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CondidateDtoWithId {

    @NotNull(message = "condiadte id cannot be null")
    private Long CondidateId;

    private CondidateDto condidateDto;
}
