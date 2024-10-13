package com.yahia.votingms.dto;

import lombok.Data;

@Data
public class VoteDto {

    private Long voterId;

    private Long condidateId;

    private Long votingSessionId;
}
