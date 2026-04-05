package com.emp.controller;

import com.emp.entity.User;
import com.emp.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("loggedUser") != null) {
            User user = (User) session.getAttribute("loggedUser");
            if ("ADMIN".equals(user.getRole())) return "redirect:/admin/dashboard";
            else return "redirect:/employee/dashboard";
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          RedirectAttributes ra) {
        User user = authService.login(username, password);
        if (user != null) {
            session.setAttribute("loggedUser", user);
            if ("ADMIN".equals(user.getRole())) return "redirect:/admin/dashboard";
            else return "redirect:/employee/dashboard";
        }
        ra.addFlashAttribute("error", "Invalid username or password!");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
