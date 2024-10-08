package com.yahia.vmms.dto;

import com.yahia.vmms.customValidation.appStartDate.BeginDate;
import com.yahia.vmms.entity.enums.Visibility;
import com.yahia.vmms.entity.enums.VotingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VotingSessionDto {

    @NotNull(message = "sessionAdminId should not be null")
    private Long sessionAdminId;

    @NotEmpty(message = "title should not be null")
    @Size(max = 40,message = "title string length should be between 2 and 40 character")
    private String title;

    @NotNull(message = "start date cannot be a null value")
    @BeginDate    //custom validator to ensure the voting session creation is after current moment
    private LocalDateTime startDate;

    @NotNull(message = "end date cannot be a null value")
    private LocalDateTime endDate;


    private VotingStatus votingStatus;


    private Visibility visibility;

    private String allowedRegions;
}
