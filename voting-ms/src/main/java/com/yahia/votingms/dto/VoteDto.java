package com.yahia.votingms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteDto {

    @NotNull(message = "voter ID should not be null")
    private Long voterId;

    @NotNull(message = "candidate ID should not be null")
    private Long condidateId;

    @NotNull(message = "Voting session ID should not be null")
    private Long votingSessionId;
}
