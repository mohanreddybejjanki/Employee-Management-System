package com.emp.controller;

import com.emp.entity.*;
import com.emp.service.*;
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
import com.emp.entity.Asset;
import com.emp.entity.Overtime;

@Controller
@RequestMapping("/employee")
public class EmployeePortalController {

    @Autowired private EmployeeService employeeService;
    @Autowired private LeaveService leaveService;
    @Autowired private AttendanceService attendanceService;
    @Autowired private SalaryService salaryService;
    @Autowired private AnnouncementService announcementService;
    @Autowired private AuthService authService;
    @Autowired private OvertimeService overtimeService;
    @Autowired private AssetService assetService;

    private Employee getLoggedEmployee(HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !"EMPLOYEE".equals(user.getRole())) return null;
        return employeeService.getByUser(user).orElse(null);
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";

        model.addAttribute("employee", emp);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        model.addAttribute("announcements", announcementService.getAll());
        model.addAttribute("myLeaves", leaveService.getByEmployee(emp));
        model.addAttribute("presentCount", attendanceService.countPresent(emp));
        model.addAttribute("absentCount", attendanceService.countAbsent(emp));
        model.addAttribute("pendingLeaves",
                leaveService.getByEmployee(emp).stream()
                        .filter(l -> "PENDING".equals(l.getStatus())).count());
        return "employee/dashboard";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        model.addAttribute("employee", emp);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "employee/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String phone,
                                @RequestParam String address,
                                HttpSession session, RedirectAttributes ra) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        emp.setPhone(phone);
        emp.setAddress(address);
        employeeService.save(emp);
        ra.addFlashAttribute("success", "Profile updated!");
        return "redirect:/employee/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        if (!newPassword.equals(confirmPassword)) {
            ra.addFlashAttribute("error", "New passwords do not match!");
            return "redirect:/employee/profile";
        }
        boolean changed = authService.changePassword(user.getUsername(), oldPassword, newPassword);
        if (changed) ra.addFlashAttribute("success", "Password changed successfully!");
        else ra.addFlashAttribute("error", "Old password is incorrect!");
        return "redirect:/employee/profile";
    }

    // ---- Leave ----
    @GetMapping("/leaves")
    public String leaves(HttpSession session, Model model) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        List<LeaveRequest> leaves = leaveService.getByEmployee(emp);
        if (leaves == null) leaves = new ArrayList<>();
        model.addAttribute("leaves", leaves);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        model.addAttribute("employee", emp);
        return "employee/leaves/list";
    }

    @GetMapping("/leaves/apply")
    public String applyForm(HttpSession session, Model model) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        model.addAttribute("leave", new LeaveRequest());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "employee/leaves/apply";
    }

    @PostMapping("/leaves/apply")
    public String applyLeave(@ModelAttribute LeaveRequest leaveRequest,
                             HttpSession session, RedirectAttributes ra) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        leaveRequest.setEmployee(emp);
        leaveRequest.setStatus("PENDING");
        leaveService.save(leaveRequest);
        ra.addFlashAttribute("success", "Leave application submitted!");
        return "redirect:/employee/leaves";
    }

    // ---- Attendance ----
    @GetMapping("/attendance")
    public String attendance(HttpSession session, Model model) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        List<Attendance> attendances = attendanceService.getByEmployee(emp);
        if (attendances == null) attendances = new ArrayList<>();
        model.addAttribute("attendances", attendances);
        model.addAttribute("presentCount", attendanceService.countPresent(emp));
        model.addAttribute("absentCount", attendanceService.countAbsent(emp));
        model.addAttribute("user", session.getAttribute("loggedUser"));
        model.addAttribute("employee", emp);
        return "employee/attendance";
    }

    // ---- Salary ----
    @GetMapping("/salary")
    public String salary(HttpSession session, Model model) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        List<Salary> salaries = salaryService.getByEmployee(emp);
        if (salaries == null) salaries = new ArrayList<>();
        model.addAttribute("salaries", salaries);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        model.addAttribute("employee", emp);
        return "employee/salary/list";
    }

    @GetMapping("/salary/payslip/{id}")
    public String payslip(@PathVariable Long id, HttpSession session, Model model) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        Optional<Salary> sal = salaryService.getById(id);
        if (sal.isEmpty() || !sal.get().getEmployee().getId().equals(emp.getId()))
            return "redirect:/employee/salary";
        model.addAttribute("salary", sal.get());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "employee/salary/payslip";
    }

    // ---- Announcements ----
    @GetMapping("/announcements")
    public String announcements(HttpSession session, Model model) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        model.addAttribute("announcements", announcementService.getAll());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        model.addAttribute("employee", emp);
        return "employee/announcements";
    }

    // ---- Overtime ----
    @GetMapping("/overtime")
    public String overtime(HttpSession session, Model model) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        List<Overtime> overtimes = overtimeService.getByEmployee(emp);
        if (overtimes == null) overtimes = new ArrayList<>();
        model.addAttribute("overtimes", overtimes);
        model.addAttribute("employee", emp);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "employee/overtime";
    }

    @PostMapping("/overtime/apply")
    public String applyOvertime(@RequestParam String date,
                                @RequestParam double hours,
                                @RequestParam String reason,
                                HttpSession session, RedirectAttributes ra) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        Overtime ot = new Overtime();
        ot.setEmployee(emp);
        ot.setDate(java.time.LocalDate.parse(date));
        ot.setHours(hours);
        ot.setReason(reason);
        ot.setStatus("PENDING");
        overtimeService.save(ot);
        ra.addFlashAttribute("success", "Overtime request submitted!");
        return "redirect:/employee/overtime";
    }

    // ---- My Team ----
    @GetMapping("/myteam")
    public String myTeam(HttpSession session, Model model) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        List<Employee> team = new ArrayList<>();
        if (emp.getDepartment() != null) {
            team = employeeService.getByDepartment(emp.getDepartment());
            team.removeIf(e -> e.getId().equals(emp.getId()));
        }
        model.addAttribute("team", team);
        model.addAttribute("employee", emp);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "employee/myteam";
    }

    // ---- My Assets ----
    @GetMapping("/assets")
    public String myAssets(HttpSession session, Model model) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        List<Asset> assets = assetService.getByEmployee(emp);
        if (assets == null) assets = new ArrayList<>();
        model.addAttribute("assets", assets);
        model.addAttribute("employee", emp);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "employee/assets";
    }

    @GetMapping("/idcard")
    public String myIdCard(HttpSession session, Model model) {
        Employee emp = getLoggedEmployee(session);
        if (emp == null) return "redirect:/login";
        model.addAttribute("employee", emp);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "employee/idcard";
    }

}
