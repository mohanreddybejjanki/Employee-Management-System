package com.emp.controller;

import com.emp.entity.PerformanceReview;
import com.emp.entity.User;
import com.emp.service.EmployeeService;
import com.emp.service.PerformanceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/admin/performance")
public class PerformanceController {

    @Autowired private PerformanceService performanceService;
    @Autowired private EmployeeService employeeService;

    private boolean isAdmin(HttpSession s) {
        User u = (User) s.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String list(HttpSession s, Model m) {
        if (!isAdmin(s)) return "redirect:/login";
        m.addAttribute("reviews", performanceService.getAll());
        m.addAttribute("user", s.getAttribute("loggedUser"));
        return "admin/performance/list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession s, Model m) {
        if (!isAdmin(s)) return "redirect:/login";
        m.addAttribute("review", new PerformanceReview());
        m.addAttribute("employees", employeeService.getActiveEmployees());
        m.addAttribute("user", s.getAttribute("loggedUser"));
        return "admin/performance/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute PerformanceReview review,
                      @RequestParam Long employeeId,
                      HttpSession s, RedirectAttributes ra) {
        if (!isAdmin(s)) return "redirect:/login";
        employeeService.getById(employeeId).ifPresent(review::setEmployee);
        review.setReviewDate(LocalDate.now());
        performanceService.save(review);
        ra.addFlashAttribute("success", "Performance review saved!");
        return "redirect:/admin/performance";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        performanceService.delete(id);
        ra.addFlashAttribute("success", "Review deleted!");
        return "redirect:/admin/performance";
    }
}