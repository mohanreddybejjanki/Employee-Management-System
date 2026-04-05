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
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired private EmployeeService employeeService;
    @Autowired private DepartmentService departmentService;
    @Autowired private LeaveService leaveService;
    @Autowired private AnnouncementService announcementService;
    @Autowired private SalaryService salaryService;
    @Autowired private AttendanceService attendanceService;

    private boolean isAdmin(HttpSession session) {
        User u = (User) session.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        User user = (User) session.getAttribute("loggedUser");

        model.addAttribute("user", user);
        model.addAttribute("totalEmployees", employeeService.countAll());
        model.addAttribute("activeEmployees", employeeService.countActive());
        model.addAttribute("totalDepartments", departmentService.count());
        model.addAttribute("pendingLeaves", leaveService.countPending());
        model.addAttribute("employees", employeeService.getActiveEmployees());
        model.addAttribute("announcements", announcementService.getAll());
        model.addAttribute("recentLeaves", leaveService.getPending());
        model.addAttribute("departments", departmentService.getAll());
        return "admin/dashboard";
    }
}
