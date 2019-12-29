package com.se.artofclipping.controllers;

import com.se.artofclipping.model.User;
import com.se.artofclipping.services.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class AdminController {

    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("admin/adminpage")
    public String adminPage(){
        return "admin/adminpage";
    }

    @GetMapping("admin/addhairdresser")
    public String addHairdresser(Model model){
        model.addAttribute("user", new User());

        return "admin/addHairdresser";
    }

    @PostMapping("admin/register")
    public String register(@ModelAttribute User user, Model model){
        log.debug(user.getEmail());
        User userExists = adminService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            log.debug("User already exists.");
        }

            adminService.saveHairdresser(user);
            model.addAttribute("user", new User());

        log.debug("REGISTERED SUCCESSFULLY FFS");

        return "admin/adminpage";
    }

    @GetMapping("admin/listhairdressers")
    public String list(Model model){
        model.addAttribute("hairdressers", adminService.listHairdressers());

        return "admin/adminListHairdressers";
    }

    @GetMapping("admin/modify")
    public String clientModify(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = adminService.findUserByEmail(auth.getName());
        model.addAttribute("user",user);
        return "admin/adminModifyProfile";
    }

//    @GetMapping("admin/changeEmailView")
//    public String clientChangeEmailView(Model model){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = adminService.findUserByEmail(auth.getName());
//        model.addAttribute("user",user);
//        model.addAttribute("newUser",new User());
//        return "user/userChangeEmail";
//    }

}

