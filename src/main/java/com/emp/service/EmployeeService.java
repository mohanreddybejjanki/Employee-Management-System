package com.emp.service;

import com.emp.entity.Employee;
import com.emp.entity.Department;
import com.emp.entity.User;
import com.emp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getActiveEmployees() {
        return employeeRepository.findByStatus("ACTIVE");
    }

    public Optional<Employee> getById(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getByUser(User user) {
        return employeeRepository.findByUser(user);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> search(String query) {
        return employeeRepository.searchEmployees(query);
    }

    public List<Employee> getByDepartment(Department department) {
        return employeeRepository.findByDepartment(department);
    }

    public long countActive() {
        return employeeRepository.countByStatus("ACTIVE");
    }

    public long countAll() {
        return employeeRepository.count();
    }

    public String generateEmployeeCode() {
        long count = employeeRepository.count() + 1;
        return String.format("EMP%04d", count);
    }
}
