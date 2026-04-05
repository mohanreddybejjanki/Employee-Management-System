package com.emp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "salaries")
@Data
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "salary_month")
    private int month;

    @Column(name = "salary_year")
    private int year;

    private double basicSalary;

    private double hra;

    private double da;

    private double medicalAllowance;

    private double pf;

    private double tax;

    private double otherDeductions;

    private double netSalary;

    private String status = "PENDING";

    public void calculateNet() {
        double grossSalary = basicSalary + hra + da + medicalAllowance;
        double totalDeductions = pf + tax + otherDeductions;
        this.netSalary = grossSalary - totalDeductions;
    }
}