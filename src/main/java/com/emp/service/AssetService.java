package com.emp.service;

import com.emp.entity.Asset;
import com.emp.entity.Employee;
import com.emp.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AssetService {
    @Autowired private AssetRepository assetRepository;

    public List<Asset> getAll() { return assetRepository.findAll(); }
    public List<Asset> getByEmployee(Employee e) { return assetRepository.findByAssignedTo(e); }
    public List<Asset> getAvailable() { return assetRepository.findByStatus("AVAILABLE"); }
    public Optional<Asset> getById(Long id) { return assetRepository.findById(id); }
    public Asset save(Asset a) { return assetRepository.save(a); }
    public void delete(Long id) { assetRepository.deleteById(id); }
    public long countAssigned() { return assetRepository.countByStatus("ASSIGNED"); }
}