package com.yahia.vmms.entity.compositeIDs;

import com.yahia.vmms.entity.Condidate;
import com.yahia.vmms.entity.VotingSessions;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ApplicationID implements Serializable {


    @Column(name = "condidate_id") // This should match the name used in SessionApplication
    private Long condidateId;

    @Column(name = "voting_session_id") // This should match the name used in SessionApplication
    private Long votingSessionId;

}
