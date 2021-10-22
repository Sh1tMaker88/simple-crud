package com.godel.simplecrud.model;

import com.godel.simplecrud.model.validation.AgeValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    @Positive(message = "Employee ID cannot be less or equal 0")
    private Long employeeId;

    @Column(name = "first_name")
    @Schema(required = true, pattern = "[A-Z]\\D{2,}")
    @Pattern(regexp =  "[A-Z]\\D{2,}", message = "First name must start with capital letter, have length 3+ and contains no numbers")
    private String firstName;

    @Column(name = "last_name")
    @Schema(required = true, pattern = "[A-Z]\\D{2,}")
    @Pattern(regexp =  "[A-Z]\\D{2,}", message = "Last name must start with capital letter, have length 3+ and contains no numbers")
    private String lastName;

    @Column(name = "department_id")
    @Schema(required = true)
    @Positive(message = "Department ID cannot be less or equal 0")
    private Long departmentId;

    @Column(name = "job_title")
    @Schema(required = true)
    private String jobTitle;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender")
    @Schema(required = true)
    private Gender gender;

    @Column(name = "date_of_birth")
    @Schema(required = true)
    @AgeValidation(message = "Age must be more than 18 years old")
    private LocalDate dateOfBirth;

}
