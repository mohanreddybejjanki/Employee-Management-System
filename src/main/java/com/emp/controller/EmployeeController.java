package com.emp.controller;

import com.emp.entity.Department;
import com.emp.entity.Employee;
import com.emp.entity.User;
import com.emp.service.AuthService;
import com.emp.service.DepartmentService;
import com.emp.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/employees")
public class EmployeeController {

    @Autowired private EmployeeService employeeService;
    @Autowired private DepartmentService departmentService;
    @Autowired private AuthService authService;

    private boolean isAdmin(HttpSession session) {
        User u = (User) session.getAttribute("loggedUser");
        return u != null && "ADMIN".equals(u.getRole());
    }

    @GetMapping
    public String list(HttpSession session, Model model,
                       @RequestParam(required = false) String search) {
        if (!isAdmin(session)) return "redirect:/login";
        List<Employee> employees;
        if (search != null && !search.isBlank()) {
            employees = employeeService.search(search);
            model.addAttribute("search", search);
        } else {
            employees = employeeService.getAllEmployees();
        }
        model.addAttribute("employees", employees);
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/employees/list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/employees/form";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam Long departmentId,
                              @RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";

        if (authService.findByUsername(username).isPresent()) {
            ra.addFlashAttribute("error", "Username already exists!");
            return "redirect:/admin/employees/add";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("EMPLOYEE");
        user.setActive(true);
        User savedUser = authService.saveUser(user);

        Optional<Department> dept = departmentService.getById(departmentId);
        dept.ifPresent(employee::setDepartment);
        employee.setUser(savedUser);
        employee.setEmployeeCode(employeeService.generateEmployeeCode());
        employeeService.save(employee);

        ra.addFlashAttribute("success", "Employee added successfully!");
        return "redirect:/admin/employees";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        Optional<Employee> emp = employeeService.getById(id);
        if (emp.isEmpty()) return "redirect:/admin/employees";
        model.addAttribute("employee", emp.get());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/employees/view";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        Optional<Employee> emp = employeeService.getById(id);
        if (emp.isEmpty()) return "redirect:/admin/employees";
        model.addAttribute("employee", emp.get());
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/employees/edit";
    }

    @PostMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id,
                               @ModelAttribute Employee employee,
                               @RequestParam Long departmentId,
                               HttpSession session,
                               RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        Optional<Employee> existing = employeeService.getById(id);
        if (existing.isEmpty()) return "redirect:/admin/employees";

        Employee emp = existing.get();
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setEmail(employee.getEmail());
        emp.setPhone(employee.getPhone());
        emp.setDesignation(employee.getDesignation());
        emp.setGender(employee.getGender());
        emp.setDateOfBirth(employee.getDateOfBirth());
        emp.setJoinDate(employee.getJoinDate());
        emp.setAddress(employee.getAddress());
        emp.setBasicSalary(employee.getBasicSalary());
        emp.setStatus(employee.getStatus());
        departmentService.getById(departmentId).ifPresent(emp::setDepartment);
        employeeService.save(emp);

        ra.addFlashAttribute("success", "Employee updated successfully!");
        return "redirect:/admin/employees";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        employeeService.delete(id);
        ra.addFlashAttribute("success", "Employee deleted!");
        return "redirect:/admin/employees";
    }
    @GetMapping("/idcard/{id}")
    public String idCard(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        Optional<Employee> emp = employeeService.getById(id);
        if (emp.isEmpty()) return "redirect:/admin/employees";
        model.addAttribute("employee", emp.get());
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "admin/employees/idcard";
    }
}
