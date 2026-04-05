package com.emp.controller;

import com.emp.entity.Attendance;
import com.emp.entity.Employee;
import com.emp.entity.User;
import com.emp.service.AttendanceService;
import com.emp.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/attendance")
public class AttendanceController {

    @Autowired private AttendanceService attendanceService;
    @Autowired private EmployeeService employeeService;

    private boolean isAdmin(HttpSession session) {
        User u = (User) session.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String list(HttpSession session, Model model,
                       @RequestParam(required = false) String date) {
        if (!isAdmin(session)) return "redirect:/login";
        LocalDate selectedDate = (date != null && !date.isBlank()) ?
                LocalDate.parse(date) : LocalDate.now();
        List<Attendance> records = attendanceService.getByDate(selectedDate);
        model.addAttribute("attendances", records);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/attendance/list";
    }

    @GetMapping("/mark")
    public String markForm(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("employees", employeeService.getActiveEmployees());
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/attendance/mark";
    }

    @PostMapping("/mark")
    public String markAttendance(@RequestParam List<Long> employeeIds,
                                 @RequestParam List<String> statuses,
                                 @RequestParam String date,
                                 HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        LocalDate attendanceDate = LocalDate.parse(date);
        for (int i = 0; i < employeeIds.size(); i++) {
            Optional<Employee> emp = employeeService.getById(employeeIds.get(i));
            if (emp.isPresent()) {
                if (!attendanceService.alreadyMarked(emp.get(), attendanceDate)) {
                    Attendance a = new Attendance();
                    a.setEmployee(emp.get());
                    a.setDate(attendanceDate);
                    a.setStatus(statuses.get(i));
                    attendanceService.save(a);
                }
            }
        }
        ra.addFlashAttribute("success", "Attendance marked successfully!");
        return "redirect:/admin/attendance";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        attendanceService.delete(id);
        ra.addFlashAttribute("success", "Attendance record deleted!");
        return "redirect:/admin/attendance";
    }
}
