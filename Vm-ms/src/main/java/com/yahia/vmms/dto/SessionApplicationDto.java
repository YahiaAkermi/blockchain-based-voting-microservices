package com.yahia.vmms.dto;

import com.yahia.vmms.entity.enums.ApplicationStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class SessionApplicationDto {


    @NotNull(message = "voting session id should not be null")
    private Long votingSessionId;

    @NotEmpty(message = "email should not be null")
    @Email(message = "email address is not valid")
    private String email;

    @NotEmpty(message = "party should not be null")
    @Size(min = 2,max = 30,message = "party that you belog to should be between 2 and 30 characters")
    private String party;

    @NotEmpty(message = "programmeFileUrl should not be null")
    @URL(message = "this is not a valid url for the candidate programme ")
    private String programmeFileUrl;


    private ApplicationStatus applicationStatus;


}
