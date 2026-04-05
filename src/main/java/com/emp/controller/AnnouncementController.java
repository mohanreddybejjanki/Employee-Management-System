package com.emp.controller;

import com.emp.entity.Announcement;
import com.emp.entity.User;
import com.emp.service.AnnouncementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/announcements")
public class AnnouncementController {

    @Autowired private AnnouncementService announcementService;

    private boolean isAdmin(HttpSession session) {
        User u = (User) session.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String list(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("announcements", announcementService.getAll());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/announcements/list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("announcement", new Announcement());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/announcements/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Announcement announcement,
                      HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        User user = (User) session.getAttribute("loggedUser");
        announcement.setPostedBy(user.getUsername());
        announcementService.save(announcement);
        ra.addFlashAttribute("success", "Announcement posted!");
        return "redirect:/admin/announcements";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        Optional<Announcement> ann = announcementService.getById(id);
        if (ann.isEmpty()) return "redirect:/admin/announcements";
        model.addAttribute("announcement", ann.get());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/announcements/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Announcement announcement,
                       HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        User user = (User) session.getAttribute("loggedUser");
        announcement.setId(id);
        announcement.setPostedBy(user.getUsername());
        announcementService.save(announcement);
        ra.addFlashAttribute("success", "Announcement updated!");
        return "redirect:/admin/announcements";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        announcementService.delete(id);
        ra.addFlashAttribute("success", "Announcement deleted!");
        return "redirect:/admin/announcements";
    }
}
