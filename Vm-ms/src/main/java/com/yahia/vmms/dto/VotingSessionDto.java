package com.yahia.vmms.dto;

import com.yahia.vmms.entity.enums.Visibility;
import com.yahia.vmms.entity.enums.VotingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
