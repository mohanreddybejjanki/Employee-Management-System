package com.emp.repository;

import com.emp.entity.Attendance;
import com.emp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployee(Employee employee);
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);
    Optional<Attendance> findFirstByEmployeeAndDate(Employee employee, LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.employee = :emp AND a.date BETWEEN :start AND :end ORDER BY a.date")
    List<Attendance> findByEmployeeAndDateRange(@Param("emp") Employee employee,
                                                 @Param("start") LocalDate start,
                                                 @Param("end") LocalDate end);

    long countByEmployeeAndStatus(Employee employee, String status);
}
