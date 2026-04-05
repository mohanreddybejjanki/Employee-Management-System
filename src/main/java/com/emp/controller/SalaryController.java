package com.emp.controller;

import com.emp.entity.Employee;
import com.emp.entity.Salary;
import com.emp.entity.User;
import com.emp.service.EmployeeService;
import com.emp.service.SalaryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/salary")
public class SalaryController {

    @Autowired private SalaryService salaryService;
    @Autowired private EmployeeService employeeService;

    private boolean isAdmin(HttpSession session) {
        User u = (User) session.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String list(HttpSession session, Model model,
                       @RequestParam(required = false) Integer month,
                       @RequestParam(required = false) Integer year) {
        if (!isAdmin(session)) return "redirect:/login";
        int m = (month != null) ? month : LocalDate.now().getMonthValue();
        int y = (year != null) ? year : LocalDate.now().getYear();
        List<Salary> salaries = salaryService.getByMonthYear(m, y);
        model.addAttribute("salaries", salaries);
        model.addAttribute("selectedMonth", m);
        model.addAttribute("selectedYear", y);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/salary/list";
    }

    @GetMapping("/generate")
    public String generateForm(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("employees", employeeService.getActiveEmployees());
        model.addAttribute("salary", new Salary());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/salary/form";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam Long employeeId,
                           @ModelAttribute Salary salary,
                           HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        Optional<Employee> emp = employeeService.getById(employeeId);
        if (emp.isEmpty()) { ra.addFlashAttribute("error", "Employee not found"); return "redirect:/admin/salary"; }

        Optional<Salary> existing = salaryService.getByEmployeeMonthYear(emp.get(), salary.getMonth(), salary.getYear());
        if (existing.isPresent()) {
            ra.addFlashAttribute("error", "Salary already generated for this month!");
            return "redirect:/admin/salary";
        }
        salary.setEmployee(emp.get());
        salary.setBasicSalary(emp.get().getBasicSalary());
        salaryService.save(salary);
        ra.addFlashAttribute("success", "Salary generated successfully!");
        return "redirect:/admin/salary";
    }

    @GetMapping("/payslip/{id}")
    public String payslip(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        Optional<Salary> sal = salaryService.getById(id);
        if (sal.isEmpty()) return "redirect:/admin/salary";
        model.addAttribute("salary", sal.get());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/salary/payslip";
    }

    @PostMapping("/pay/{id}")
    public String markPaid(@PathVariable Long id, RedirectAttributes ra) {
        salaryService.markPaid(id);
        ra.addFlashAttribute("success", "Salary marked as paid!");
        return "redirect:/admin/salary";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        salaryService.delete(id);
        ra.addFlashAttribute("success", "Salary record deleted!");
        return "redirect:/admin/salary";
    }
}
