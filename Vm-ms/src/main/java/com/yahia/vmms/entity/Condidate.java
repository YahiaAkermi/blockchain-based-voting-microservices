package com.yahia.vmms.entity;

import com.yahia.vmms.entity.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Entity
@ToString @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Condidate extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long condidateId;

    private String name;

    private String email;

    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;


//    private String party;

//    private  String programmeFileUrl;

//    @Enumerated(EnumType.STRING)
//    private ApplicationStatus applicationStatus;


    @OneToMany(mappedBy = "applicationId.condidate")
    private Collection<SessionApplication> listApplications;




}
