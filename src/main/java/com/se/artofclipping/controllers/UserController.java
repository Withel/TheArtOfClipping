package com.se.artofclipping.controllers;

import com.se.artofclipping.model.User;
import com.se.artofclipping.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/new")
    public String newUser(Model model){
        model.addAttribute("user", new User());

        return "user/registerForm";
    }

    @PostMapping("user")
    public String saveUser(@ModelAttribute User user){
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        System.out.println(user.getSecondName());

        return "redirect:../index.html";
    }
}
