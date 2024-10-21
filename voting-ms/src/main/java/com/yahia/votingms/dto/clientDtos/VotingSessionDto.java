package com.yahia.votingms.dto.clientDtos;


import com.yahia.votingms.dto.clientDtos.enums.Visibility;
import com.yahia.votingms.dto.clientDtos.enums.VotingStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VotingSessionDto {


    private Long sessionAdminId;


    private String title;


    private LocalDateTime startDate;

    private LocalDateTime endDate;


    private VotingStatus votingStatus;


    private Visibility visibility;

    private String allowedRegions;
}
