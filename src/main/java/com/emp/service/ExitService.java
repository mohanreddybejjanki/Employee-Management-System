package com.emp.service;

import com.emp.entity.ExitRequest;
import com.emp.repository.ExitRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExitService {
    @Autowired private ExitRequestRepository exitRequestRepository;

    public List<ExitRequest> getAll() { return exitRequestRepository.findAll(); }
    public List<ExitRequest> getPending() { return exitRequestRepository.findByStatus("PENDING"); }
    public Optional<ExitRequest> getById(Long id) { return exitRequestRepository.findById(id); }
    public ExitRequest save(ExitRequest e) { return exitRequestRepository.save(e); }
    public void delete(Long id) { exitRequestRepository.deleteById(id); }
    public long countPending() { return exitRequestRepository.countByStatus("PENDING"); }

    public boolean accept(Long id, String comment) {
        Optional<ExitRequest> opt = exitRequestRepository.findById(id);
        if (opt.isPresent()) {
            ExitRequest er = opt.get();
            er.setStatus("ACCEPTED");
            er.setAdminComment(comment);
            exitRequestRepository.save(er);
            return true;
        }
        return false;
    }
}