package com.emp.repository;

import com.emp.entity.Employee;
import com.emp.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByEmployee(Employee employee);
    List<Training> findAllByOrderByStartDateDesc();
}