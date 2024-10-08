package com.yahia.vmms.dto;

import com.yahia.vmms.customValidation.ageValidator.MinAge;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CondidateDto {

    @NotEmpty(message = "name cannot be a null value")
    @Size(min = 5,message ="the full name should be at least of 5 chars" )
    private String name;

    @NotEmpty(message = "email cannot be a null value")
    @Email(message = "email address is not valid")
    private String email;

    @NotNull(message = "date of birth cannot be a null value")
    @MinAge() //I can give an integer as param to define the minimun age . 18 by default
    private LocalDate dateOfBirth;
}
