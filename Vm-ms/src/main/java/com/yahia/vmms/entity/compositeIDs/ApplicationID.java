package com.yahia.vmms.entity.compositeIDs;

import com.yahia.vmms.entity.Condidate;
import com.yahia.vmms.entity.VotingSessions;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class ApplicationID implements Serializable {

    @ManyToOne
    private Condidate condidate;

    @ManyToOne
    private VotingSessions votingSession;
}