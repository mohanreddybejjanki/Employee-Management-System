package com.emp.service;

import com.emp.entity.Attendance;
import com.emp.entity.Employee;
import com.emp.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAll() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> getByEmployee(Employee employee) {
        return attendanceRepository.findByEmployee(employee);
    }

    public List<Attendance> getByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    public List<Attendance> getByEmployeeAndRange(Employee employee, LocalDate start, LocalDate end) {
        return attendanceRepository.findByEmployeeAndDateRange(employee, start, end);
    }

    public Optional<Attendance> getTodayAttendance(Employee employee) {
        return attendanceRepository.findFirstByEmployeeAndDate(employee, LocalDate.now());
    }

    public Attendance save(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public void delete(Long id) {
        attendanceRepository.deleteById(id);
    }

    public long countPresent(Employee employee) {
        return attendanceRepository.countByEmployeeAndStatus(employee, "PRESENT");
    }

    public long countAbsent(Employee employee) {
        return attendanceRepository.countByEmployeeAndStatus(employee, "ABSENT");
    }

    public Optional<Attendance> getById(Long id) {
        return attendanceRepository.findById(id);
    }

    public boolean alreadyMarked(Employee employee, LocalDate date) {
        return attendanceRepository.findFirstByEmployeeAndDate(employee, date).isPresent();
    }
}
