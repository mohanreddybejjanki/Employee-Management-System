package com.emp.controller;

import com.emp.entity.User;
import com.emp.service.ExitService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/exit")
public class ExitController {

    @Autowired private ExitService exitService;

    private boolean isAdmin(HttpSession s) {
        User u = (User) s.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String list(HttpSession s, Model m) {
        if (!isAdmin(s)) return "redirect:/login";
        m.addAttribute("exits", exitService.getAll());
        m.addAttribute("user", s.getAttribute("loggedUser"));
        return "admin/exit/list";
    }

    @PostMapping("/accept/{id}")
    public String accept(@PathVariable Long id,
                         @RequestParam(defaultValue = "") String comment,
                         RedirectAttributes ra) {
        exitService.accept(id, comment);
        ra.addFlashAttribute("success", "Resignation accepted!");
        return "redirect:/admin/exit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        exitService.delete(id);
        ra.addFlashAttribute("success", "Record deleted!");
        return "redirect:/admin/exit";
    }
}