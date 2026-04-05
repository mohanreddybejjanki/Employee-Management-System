package com.emp.service;

import com.emp.entity.Employee;
import com.emp.entity.LeaveRequest;
import com.emp.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public List<LeaveRequest> getAll() {
        return leaveRequestRepository.findAll();
    }

    public List<LeaveRequest> getByEmployee(Employee employee) {
        return leaveRequestRepository.findByEmployeeOrderByAppliedOnDesc(employee);
    }

    public List<LeaveRequest> getPending() {
        return leaveRequestRepository.findByStatus("PENDING");
    }

    public Optional<LeaveRequest> getById(Long id) {
        return leaveRequestRepository.findById(id);
    }

    public LeaveRequest save(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    public void delete(Long id) {
        leaveRequestRepository.deleteById(id);
    }

    public boolean approve(Long id, String comment) {
        Optional<LeaveRequest> opt = leaveRequestRepository.findById(id);
        if (opt.isPresent()) {
            LeaveRequest lr = opt.get();
            lr.setStatus("APPROVED");
            lr.setAdminComment(comment);
            leaveRequestRepository.save(lr);
            return true;
        }
        return false;
    }

    public boolean reject(Long id, String comment) {
        Optional<LeaveRequest> opt = leaveRequestRepository.findById(id);
        if (opt.isPresent()) {
            LeaveRequest lr = opt.get();
            lr.setStatus("REJECTED");
            lr.setAdminComment(comment);
            leaveRequestRepository.save(lr);
            return true;
        }
        return false;
    }

    public long countPending() {
        return leaveRequestRepository.countByStatus("PENDING");
    }
}
