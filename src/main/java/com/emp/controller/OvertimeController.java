package com.emp.controller;

import com.emp.entity.Overtime;
import com.emp.entity.User;
import com.emp.service.OvertimeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/overtime")
public class OvertimeController {

    @Autowired private OvertimeService overtimeService;

    private boolean isAdmin(HttpSession s) {
        User u = (User) s.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String list(HttpSession s, Model m) {
        if (!isAdmin(s)) return "redirect:/login";
        m.addAttribute("overtimes", overtimeService.getAll());
        m.addAttribute("user", s.getAttribute("loggedUser"));
        return "admin/overtime/list";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id,
                          @RequestParam(defaultValue = "") String comment,
                          RedirectAttributes ra) {
        overtimeService.approve(id, comment);
        ra.addFlashAttribute("success", "Overtime approved!");
        return "redirect:/admin/overtime";
    }

    @PostMapping("/reject/{id}")
    public String reject(@PathVariable Long id,
                         @RequestParam(defaultValue = "") String comment,
                         RedirectAttributes ra) {
        overtimeService.reject(id, comment);
        ra.addFlashAttribute("success", "Overtime rejected!");
        return "redirect:/admin/overtime";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        overtimeService.delete(id);
        ra.addFlashAttribute("success", "Record deleted!");
        return "redirect:/admin/overtime";
    }
}