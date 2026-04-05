package com.emp.repository;

import com.emp.entity.Employee;
import com.emp.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

    List<Salary> findByEmployee(Employee employee);

    @Query("SELECT s FROM Salary s WHERE s.month = :month AND s.year = :year")
    List<Salary> findByMonthAndYear(@Param("month") int month,
                                    @Param("year") int year);

    @Query("SELECT s FROM Salary s WHERE s.employee = :emp AND s.month = :month AND s.year = :year")
    Optional<Salary> findByEmployeeAndMonthAndYear(@Param("emp") Employee employee,
                                                   @Param("month") int month,
                                                   @Param("year") int year);

    @Query("SELECT s FROM Salary s WHERE s.employee = :emp ORDER BY s.year DESC, s.month DESC")
    List<Salary> findByEmployeeOrderByYearDescMonthDesc(@Param("emp") Employee employee);
}