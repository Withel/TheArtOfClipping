package com.se.artofclipping.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("admin/adminpage")
    public String adminPage(){
        return "admin/adminpage";
    }
}
