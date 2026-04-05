package com.emp.controller;

import com.emp.entity.Holiday;
import com.emp.entity.User;
import com.emp.service.HolidayService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/admin/holidays")
public class HolidayController {

    @Autowired private HolidayService holidayService;

    private boolean isAdmin(HttpSession s) {
        User u = (User) s.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String list(HttpSession s, Model m) {
        if (!isAdmin(s)) return "redirect:/login";
        m.addAttribute("holidays", holidayService.getAll());
        m.addAttribute("user", s.getAttribute("loggedUser"));
        return "admin/holidays/list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession s, Model m) {
        if (!isAdmin(s)) return "redirect:/login";
        m.addAttribute("holiday", new Holiday());
        m.addAttribute("user", s.getAttribute("loggedUser"));
        return "admin/holidays/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Holiday holiday, HttpSession s, RedirectAttributes ra) {
        if (!isAdmin(s)) return "redirect:/login";
        holidayService.save(holiday);
        ra.addFlashAttribute("success", "Holiday added!");
        return "redirect:/admin/holidays";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession s, Model m) {
        if (!isAdmin(s)) return "redirect:/login";
        Optional<Holiday> h = holidayService.getById(id);
        if (h.isEmpty()) return "redirect:/admin/holidays";
        m.addAttribute("holiday", h.get());
        m.addAttribute("user", s.getAttribute("loggedUser"));
        return "admin/holidays/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Holiday holiday,
                       HttpSession s, RedirectAttributes ra) {
        if (!isAdmin(s)) return "redirect:/login";
        holiday.setId(id);
        holidayService.save(holiday);
        ra.addFlashAttribute("success", "Holiday updated!");
        return "redirect:/admin/holidays";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        holidayService.delete(id);
        ra.addFlashAttribute("success", "Holiday deleted!");
        return "redirect:/admin/holidays";
    }
}