package com.emp.controller;

import com.emp.entity.User;
import com.emp.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reports")
public class ReportsController {

    @Autowired private EmployeeService employeeService;
    @Autowired private DepartmentService departmentService;
    @Autowired private LeaveService leaveService;
    @Autowired private AttendanceService attendanceService;
    @Autowired private SalaryService salaryService;

    private boolean isAdmin(HttpSession s) {
        User u = (User) s.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String reports(HttpSession s, Model m) {
        if (!isAdmin(s)) return "redirect:/login";
        m.addAttribute("user", s.getAttribute("loggedUser"));
        m.addAttribute("employees",      employeeService.getAllEmployees());
        m.addAttribute("departments",    departmentService.getAll());
        m.addAttribute("totalEmp",       employeeService.countAll());
        m.addAttribute("activeEmp",      employeeService.countActive());
        m.addAttribute("totalDept",      departmentService.count());
        m.addAttribute("pendingLeaves",  leaveService.countPending());
        m.addAttribute("allLeaves",      leaveService.getAll());
        m.addAttribute("allSalaries",    salaryService.getAll());
        return "admin/reports";
    }
}