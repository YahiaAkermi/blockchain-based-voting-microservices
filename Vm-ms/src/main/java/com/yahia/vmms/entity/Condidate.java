package com.yahia.vmms.entity;

import com.yahia.vmms.entity.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@ToString @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Condidate extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long condidateId;

    private String name;

    private String email;


    private String party;

    private  String programmeFileUrl;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;


    @ManyToOne
    @JoinColumn(name = "votingSessionId")
    private VotingSessions votingSession;




}
