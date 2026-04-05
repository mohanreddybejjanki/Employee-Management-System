package com.emp.repository;

import com.emp.entity.ExitRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExitRequestRepository extends JpaRepository<ExitRequest, Long> {
    List<ExitRequest> findByStatus(String status);
    long countByStatus(String status);
}