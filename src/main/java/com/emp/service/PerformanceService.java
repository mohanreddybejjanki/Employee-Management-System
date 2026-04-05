package com.emp.service;

import com.emp.entity.Employee;
import com.emp.entity.PerformanceReview;
import com.emp.repository.PerformanceReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PerformanceService {
    @Autowired private PerformanceReviewRepository performanceReviewRepository;

    public List<PerformanceReview> getAll() { return performanceReviewRepository.findAllByOrderByReviewDateDesc(); }
    public List<PerformanceReview> getByEmployee(Employee e) { return performanceReviewRepository.findByEmployee(e); }
    public Optional<PerformanceReview> getById(Long id) { return performanceReviewRepository.findById(id); }
    public PerformanceReview save(PerformanceReview p) { return performanceReviewRepository.save(p); }
    public void delete(Long id) { performanceReviewRepository.deleteById(id); }
}