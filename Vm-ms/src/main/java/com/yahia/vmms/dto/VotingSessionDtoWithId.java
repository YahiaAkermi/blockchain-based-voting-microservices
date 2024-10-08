package com.yahia.vmms.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VotingSessionDtoWithId {

    @NotEmpty(message = "voting session id should not be null")
    private Long votingSessionId;

    private VotingSessionDto votingSessionDto;

}
