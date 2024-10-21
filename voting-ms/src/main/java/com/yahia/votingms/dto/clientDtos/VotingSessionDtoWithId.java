package com.yahia.votingms.dto.clientDtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VotingSessionDtoWithId {


    private Long votingSessionId;

    private VotingSessionDto votingSessionDto;

}
