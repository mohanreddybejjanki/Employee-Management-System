package com.emp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    private String designation;

    private String gender;

    private LocalDate dateOfBirth;

    private LocalDate joinDate;

    private String address;

    private double basicSalary;

    private String employeeCode;

    private String status = "ACTIVE"; // ACTIVE, INACTIVE

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
