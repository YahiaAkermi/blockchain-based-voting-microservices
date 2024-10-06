package com.yahia.vmms.dto;


import com.yahia.vmms.entity.compositeIDs.ApplicationID;
import com.yahia.vmms.entity.enums.ApplicationStatus;
import lombok.Data;

@Data
public class SessionApplicationDtoWithSessionDetails {

    private ApplicationID applicationID;

    private String party;

    private String programmeFileUrl;

    private ApplicationStatus applicationStatus;

    private VotingSessionDto votingSessionDto;

}
