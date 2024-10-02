package com.yahia.vmms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity @ToString @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Admin extends BaseEntity{

    @Id
    private Long adminId;

    private String username;
    private String email;
    private String pwd;

}
