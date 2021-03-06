package com.godel.simplecrud.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.godel.simplecrud.model.validation.AgeValidation;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
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

    @Hidden
    @JsonIgnoreProperties("products")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", departmentId=" + departmentId +
                ", jobTitle='" + jobTitle + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", orders=" + orders.stream().map(Order::getOrderId).collect(Collectors.toList()) +
                '}';
    }
}
