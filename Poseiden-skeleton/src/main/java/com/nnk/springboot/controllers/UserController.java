package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", service.getAllUsers());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user,
                           BindingResult result,
                           Model model) {
        log.info("validate user {}", user);

        // checks if valid, or returns to the add form
        if (result.hasErrors()) {
            return "user/add";
        }
        // save new
        user.setPassword(encoder.encode(user.getPassword()));
        service.createUser(user);
        log.info(">>> User saved: {}", user);

        // update list
        model.addAttribute("users", service.getAllUsers());
        return "redirect:/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", service.getUserById(id));
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        service.updateUser(user);
        model.addAttribute("users", service.getAllUsers());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id,
                             Model model) {
        service.deleteUser(id);
        model.addAttribute("users", service.getAllUsers());
        return "redirect:/user/list";
    }
}
