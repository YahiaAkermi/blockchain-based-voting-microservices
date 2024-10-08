package com.yahia.vmms.dto;


import com.yahia.vmms.entity.compositeIDs.ApplicationID;
import com.yahia.vmms.entity.enums.ApplicationStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class SessionApplicationDtoWithSessionDetails {

    @NotNull(message = "applicationID should not be null")
    private ApplicationID applicationID;

    @NotEmpty(message = "party should not be null")
    private String party;

    @NotEmpty(message = "programmeFileUrl should not be null")
    @URL(message = "this is not a valid url for the candidate programme ")
    private String programmeFileUrl;


    private ApplicationStatus applicationStatus;

    private VotingSessionDto votingSessionDto;

}
