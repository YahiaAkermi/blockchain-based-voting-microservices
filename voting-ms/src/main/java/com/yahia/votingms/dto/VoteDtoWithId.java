package com.yahia.votingms.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteDtoWithId {

    @NotEmpty(message = "vote ID should not be empty string")
    private String VoteId;

    private VoteDto voteDto;

}
