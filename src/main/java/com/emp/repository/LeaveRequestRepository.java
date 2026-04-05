package com.emp.repository;

import com.emp.entity.Employee;
import com.emp.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployee(Employee employee);
    List<LeaveRequest> findByStatus(String status);
    List<LeaveRequest> findByEmployeeOrderByAppliedOnDesc(Employee employee);
    long countByStatus(String status);
}
