package com.yahia.vmms.dto;

import com.yahia.vmms.entity.enums.ApplicationStatus;
import lombok.Data;

@Data
public class SessionApplicationDto {



    private Long votingSessionId;

    private String email;

    private String party;

    private String programmeFileUrl;

    private ApplicationStatus applicationStatus;


}
