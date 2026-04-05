package com.emp.service;

import com.emp.entity.Holiday;
import com.emp.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HolidayService {
    @Autowired private HolidayRepository holidayRepository;

    public List<Holiday> getAll() { return holidayRepository.findAllByOrderByDateAsc(); }
    public Optional<Holiday> getById(Long id) { return holidayRepository.findById(id); }
    public Holiday save(Holiday h) { return holidayRepository.save(h); }
    public void delete(Long id) { holidayRepository.deleteById(id); }
}