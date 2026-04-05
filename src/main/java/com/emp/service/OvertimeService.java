package com.emp.service;

import com.emp.entity.Employee;
import com.emp.entity.Overtime;
import com.emp.repository.OvertimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OvertimeService {
    @Autowired private OvertimeRepository overtimeRepository;

    public List<Overtime> getAll() { return overtimeRepository.findAll(); }
    public List<Overtime> getByEmployee(Employee e) { return overtimeRepository.findByEmployee(e); }
    public List<Overtime> getPending() { return overtimeRepository.findByStatus("PENDING"); }
    public Optional<Overtime> getById(Long id) { return overtimeRepository.findById(id); }
    public Overtime save(Overtime o) { return overtimeRepository.save(o); }
    public void delete(Long id) { overtimeRepository.deleteById(id); }
    public long countPending() { return overtimeRepository.countByStatus("PENDING"); }

    public boolean approve(Long id, String comment) {
        Optional<Overtime> opt = overtimeRepository.findById(id);
        if (opt.isPresent()) {
            Overtime o = opt.get();
            o.setStatus("APPROVED");
            o.setAdminComment(comment);
            overtimeRepository.save(o);
            return true;
        }
        return false;
    }

    public boolean reject(Long id, String comment) {
        Optional<Overtime> opt = overtimeRepository.findById(id);
        if (opt.isPresent()) {
            Overtime o = opt.get();
            o.setStatus("REJECTED");
            o.setAdminComment(comment);
            overtimeRepository.save(o);
            return true;
        }
        return false;
    }
}