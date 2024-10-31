package com.yahia.votingms.service.client.vsDetailsCache;


import com.yahia.votingms.dto.clientDtos.enums.VotingStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("VsDetails")
@Getter @Setter@ToString @AllArgsConstructor @NoArgsConstructor
public class VsDetails implements Serializable {

    @Id
    private Long votingSessionId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;


    private VotingStatus votingStatus;

    private String listCondidates;
}
