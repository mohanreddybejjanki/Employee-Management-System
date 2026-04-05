package com.emp.repository;

import com.emp.entity.Department;
import com.emp.entity.Employee;
import com.emp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser(User user);
    List<Employee> findByDepartment(Department department);
    List<Employee> findByStatus(String status);

    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(e.designation) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Employee> searchEmployees(@Param("q") String query);

    long countByStatus(String status);
    long countByDepartment(Department department);
}
