package com.yahia.vmms.dto;


import com.yahia.vmms.customValidation.appStartDate.BeginDate;
import com.yahia.vmms.entity.enums.Visibility;
import com.yahia.vmms.entity.enums.VotingStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class VotingSessionDtoWithIdAndCondidates {

        @NotNull(message = "voting Session ID shoud not be null")
        private Long votingSessionId;


        @NotNull(message = "start date cannot be a null value")
        @BeginDate    //custom validator to ensure the voting session creation is after current moment
        private LocalDateTime startDate;

        @NotNull(message = "end date cannot be a null value")
        private LocalDateTime endDate;


        private VotingStatus votingStatus;

        private ArrayList<Long> listCondidates;

}
