package com.emp.controller;

import com.emp.entity.Asset;
import com.emp.entity.User;
import com.emp.service.AssetService;
import com.emp.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/admin/assets")
public class AssetController {

    @Autowired private AssetService assetService;
    @Autowired private EmployeeService employeeService;

    private boolean isAdmin(HttpSession s) {
        User u = (User) s.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String list(HttpSession s, Model m) {
        if (!isAdmin(s)) return "redirect:/login";
        m.addAttribute("assets", assetService.getAll());
        m.addAttribute("user", s.getAttribute("loggedUser"));
        return "admin/assets/list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession s, Model m) {
        if (!isAdmin(s)) return "redirect:/login";
        m.addAttribute("asset", new Asset());
        m.addAttribute("employees", employeeService.getActiveEmployees());
        m.addAttribute("user", s.getAttribute("loggedUser"));
        return "admin/assets/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Asset asset,
                      @RequestParam(required = false) Long employeeId,
                      HttpSession s, RedirectAttributes ra) {
        if (!isAdmin(s)) return "redirect:/login";
        if (employeeId != null) {
            employeeService.getById(employeeId).ifPresent(emp -> {
                asset.setAssignedTo(emp);
                asset.setStatus("ASSIGNED");
            });
        } else {
            asset.setStatus("AVAILABLE");
        }
        assetService.save(asset);
        ra.addFlashAttribute("success", "Asset added!");
        return "redirect:/admin/assets";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        assetService.delete(id);
        ra.addFlashAttribute("success", "Asset deleted!");
        return "redirect:/admin/assets";
    }
}