package com.yahia.vmms.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VotingSessionDtoWithId {

    @NotNull(message = "voting session id should not be null")
    private Long votingSessionId;

    private VotingSessionDto votingSessionDto;

}
