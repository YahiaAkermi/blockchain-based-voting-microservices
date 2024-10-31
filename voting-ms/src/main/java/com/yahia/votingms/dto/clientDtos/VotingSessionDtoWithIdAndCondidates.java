package com.yahia.votingms.dto.clientDtos;



import com.yahia.votingms.dto.clientDtos.enums.VotingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class VotingSessionDtoWithIdAndCondidates {

        @NotNull(message = "voting Session ID shoud not be null")
        private Long votingSessionId;


        @NotNull(message = "start date cannot be a null value")
        private LocalDateTime startDate;

        @NotNull(message = "end date cannot be a null value")
        private LocalDateTime endDate;


        private VotingStatus votingStatus;

        private ArrayList<Long> listCondidates;

}
