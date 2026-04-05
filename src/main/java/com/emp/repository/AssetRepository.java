package com.emp.repository;

import com.emp.entity.Asset;
import com.emp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByAssignedTo(Employee employee);
    List<Asset> findByStatus(String status);
    long countByStatus(String status);
}