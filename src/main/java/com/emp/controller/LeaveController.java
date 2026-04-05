package com.emp.controller;

import com.emp.entity.User;
import com.emp.service.LeaveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/leaves")
public class LeaveController {

    @Autowired private LeaveService leaveService;

    private boolean isAdmin(HttpSession session) {
        User u = (User) session.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String list(HttpSession session, Model model,
                       @RequestParam(required = false, defaultValue = "ALL") String filter) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("leaves",
                "PENDING".equals(filter) ? leaveService.getPending() : leaveService.getAll());
        model.addAttribute("filter", filter);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/leaves/list";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id,
                          @RequestParam(required = false, defaultValue = "") String comment,
                          RedirectAttributes ra) {
        leaveService.approve(id, comment);
        ra.addFlashAttribute("success", "Leave approved!");
        return "redirect:/admin/leaves";
    }

    @PostMapping("/reject/{id}")
    public String reject(@PathVariable Long id,
                         @RequestParam(required = false, defaultValue = "") String comment,
                         RedirectAttributes ra) {
        leaveService.reject(id, comment);
        ra.addFlashAttribute("success", "Leave rejected!");
        return "redirect:/admin/leaves";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        leaveService.delete(id);
        ra.addFlashAttribute("success", "Leave request deleted!");
        return "redirect:/admin/leaves";
    }
}
