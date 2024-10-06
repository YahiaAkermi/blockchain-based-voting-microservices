package com.yahia.vmms.dto;

import com.yahia.vmms.entity.enums.ApplicationStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class CondidateDto {

    private String name;

    private String email;


    private LocalDate dateOfBirth;
}
