package com.yahia.vmms.entity;

import com.yahia.vmms.entity.compositeIDs.ApplicationID;
import com.yahia.vmms.entity.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString
public class SessionApplication extends BaseEntity {


    @EmbeddedId
    private ApplicationID applicationID;


    private String party;

    private String programmeFileUrl;

    private ApplicationStatus applicationStatus;

    @ManyToOne
    @MapsId("condidateId")
    private Condidate condidate;

    @ManyToOne
    @MapsId("votingSessionId")
    private VotingSessions votingSession;




}
