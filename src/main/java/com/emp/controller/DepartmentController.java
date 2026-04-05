package com.emp.controller;

import com.emp.entity.Department;
import com.emp.entity.User;
import com.emp.service.DepartmentService;
import com.emp.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/departments")
public class DepartmentController {

    @Autowired private DepartmentService departmentService;
    @Autowired private EmployeeService employeeService;

    private boolean isAdmin(HttpSession session) {
        User u = (User) session.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String list(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/departments/list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("department", new Department());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/departments/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Department department, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        departmentService.save(department);
        ra.addFlashAttribute("success", "Department added successfully!");
        return "redirect:/admin/departments";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        Optional<Department> dept = departmentService.getById(id);
        if (dept.isEmpty()) return "redirect:/admin/departments";
        model.addAttribute("department", dept.get());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/departments/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Department department,
                       HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        department.setId(id);
        departmentService.save(department);
        ra.addFlashAttribute("success", "Department updated successfully!");
        return "redirect:/admin/departments";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        departmentService.delete(id);
        ra.addFlashAttribute("success", "Department deleted!");
        return "redirect:/admin/departments";
    }
}
