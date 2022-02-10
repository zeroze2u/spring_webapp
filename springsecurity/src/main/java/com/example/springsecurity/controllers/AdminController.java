package com.example.springsecurity.controllers;

import com.example.springsecurity.model.User;
import com.example.springsecurity.service.RoleService;
import com.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/admin")
    public String getAllUsers(@ModelAttribute("user") User user, ModelMap model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("currentUser",
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("roles", roleService.getRoles());
        return "admin/admin";
    }

    @PostMapping("/create")
    public String createUserPost(@ModelAttribute("user") User user,
                                 @RequestParam(required = false, name = "listRoles") String[] arrRoles) {
        if (arrRoles != null) {
            user.setRoles(roleService.getRolesByName(arrRoles));
        }
        userService.create(user);
        return "redirect:/admin/admin";
    }

    //------Модальные диалоги--------

    @GetMapping(value = "/delete-user/{id}")
    public String deleteUserConfirmation(@PathVariable("id") long id, ModelMap model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getRoles());
        return "admin/delete-user :: delete-user";
    }

    @PostMapping(value = "/delete-user/{id}")
    public String deleteUser(@PathVariable("id") long id, ModelMap model) {
        userService.deleteById(id);
        return "redirect:/admin/admin";
    }

    @GetMapping(value = "/edit-user/{id}")
    public String editUserModal(@PathVariable("id") long id, ModelMap model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getRoles());
        return "admin/edit-user :: edit-user";
    }

    @PostMapping(value = "/edit-user")
    public String editUser(@ModelAttribute User user,
                           @RequestParam(required = false, name = "listRoles") String[] arrRoles) {
        if (arrRoles != null) {
            user.setRoles(roleService.getRolesByName(arrRoles));
        }
        userService.update(user);
        return "redirect:/admin/admin";
    }
}