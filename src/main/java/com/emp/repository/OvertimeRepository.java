package com.emp.repository;

import com.emp.entity.Employee;
import com.emp.entity.Overtime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OvertimeRepository extends JpaRepository<Overtime, Long> {
    List<Overtime> findByEmployee(Employee employee);
    List<Overtime> findByStatus(String status);
    long countByStatus(String status);
}