package com.yahia.vmms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yahia.vmms.entity.enums.Visibility;
import com.yahia.vmms.entity.enums.VotingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@ToString @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VotingSessions extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vsId;

    private String title;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private VotingStatus votingStatus;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private String allowedRegions;

    private Long sessionAdminId;



    @JsonIgnore
    @OneToMany(mappedBy = "votingSession")
    private Collection<SessionApplication> listApplications;
}
