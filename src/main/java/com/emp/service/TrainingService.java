package com.emp.service;

import com.emp.entity.Employee;
import com.emp.entity.Training;
import com.emp.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {
    @Autowired private TrainingRepository trainingRepository;

    public List<Training> getAll() { return trainingRepository.findAllByOrderByStartDateDesc(); }
    public List<Training> getByEmployee(Employee e) { return trainingRepository.findByEmployee(e); }
    public Optional<Training> getById(Long id) { return trainingRepository.findById(id); }
    public Training save(Training t) { return trainingRepository.save(t); }
    public void delete(Long id) { trainingRepository.deleteById(id); }
}