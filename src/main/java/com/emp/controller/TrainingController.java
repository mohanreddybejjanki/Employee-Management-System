package com.emp.controller;

import com.emp.entity.Training;
import com.emp.entity.User;
import com.emp.service.EmployeeService;
import com.emp.service.TrainingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/admin/training")
public class TrainingController {

    @Autowired private TrainingService trainingService;
    @Autowired private EmployeeService employeeService;

    private boolean isAdmin(HttpSession s) {
        User u = (User) s.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String list(HttpSession s, Model m) {
        if (!isAdmin(s)) return "redirect:/login";
        m.addAttribute("trainings", trainingService.getAll());
        m.addAttribute("user", s.getAttribute("loggedUser"));
        return "admin/training/list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession s, Model m) {
        if (!isAdmin(s)) return "redirect:/login";
        m.addAttribute("training", new Training());
        m.addAttribute("employees", employeeService.getActiveEmployees());
        m.addAttribute("user", s.getAttribute("loggedUser"));
        return "admin/training/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Training training,
                      @RequestParam Long employeeId,
                      HttpSession s, RedirectAttributes ra) {
        if (!isAdmin(s)) return "redirect:/login";
        employeeService.getById(employeeId).ifPresent(training::setEmployee);
        trainingService.save(training);
        ra.addFlashAttribute("success", "Training record added!");
        return "redirect:/admin/training";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        trainingService.delete(id);
        ra.addFlashAttribute("success", "Training deleted!");
        return "redirect:/admin/training";
    }
}