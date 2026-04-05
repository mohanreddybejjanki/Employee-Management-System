package com.emp.service;

import com.emp.entity.Employee;
import com.emp.entity.Salary;
import com.emp.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    public List<Salary> getAll() {
        return salaryRepository.findAll();
    }

    public List<Salary> getByEmployee(Employee employee) {
        return salaryRepository.findByEmployeeOrderByYearDescMonthDesc(employee);
    }

    public List<Salary> getByMonthYear(int month, int year) {
        return salaryRepository.findByMonthAndYear(month, year);
    }

    public Optional<Salary> getById(Long id) {
        return salaryRepository.findById(id);
    }

    public Optional<Salary> getByEmployeeMonthYear(Employee employee, int month, int year) {
        return salaryRepository.findByEmployeeAndMonthAndYear(employee, month, year);
    }

    public Salary save(Salary salary) {
        salary.calculateNet();
        return salaryRepository.save(salary);
    }

    public void delete(Long id) {
        salaryRepository.deleteById(id);
    }

    public boolean markPaid(Long id) {
        Optional<Salary> opt = salaryRepository.findById(id);
        if (opt.isPresent()) {
            Salary s = opt.get();
            s.setStatus("PAID");
            salaryRepository.save(s);
            return true;
        }
        return false;
    }
}
