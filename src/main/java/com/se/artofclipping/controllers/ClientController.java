package com.se.artofclipping.controllers;

import com.se.artofclipping.model.User;
import com.se.artofclipping.services.UserService;
import com.se.artofclipping.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ClientController {
    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("user/modify")
    public String clientModify(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = clientService.findUserByEmail(auth.getName());
        model.addAttribute("user",user);
        return "user/clientModifyProfile";
    }

    @GetMapping("user/changeNameView")
    public String clientChangeNameView(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = clientService.findUserByEmail(auth.getName());
        model.addAttribute("user",user);
        model.addAttribute("newUser",new User());
        return "user/clientChangeName";
    }

    @PostMapping("user/changeName")
    public String clientChangeName(@ModelAttribute User newUser,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = clientService.findUserByEmail(auth.getName());
        clientService.changeName(currentUser,newUser.getName());
        model.addAttribute("user",currentUser);
        return "user/clientModifyProfile";
    }

    @GetMapping("user/changeSurnameView")
    public String clientChangeSurnameView(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = clientService.findUserByEmail(auth.getName());
        model.addAttribute("user",user);
        model.addAttribute("newUser",new User());
        return "user/clientChangeSurname";
    }

    @PostMapping("user/changeSurname")
    public String clientChangeSurname(@ModelAttribute User newUser,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = clientService.findUserByEmail(auth.getName());
        clientService.changeSurname(currentUser,newUser.getSurname());
        model.addAttribute("user",currentUser);
        return "user/clientModifyProfile";
    }

    @GetMapping("user/changeEmailView")
    public String clientChangeEmailView(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = clientService.findUserByEmail(auth.getName());
        model.addAttribute("user",user);
        model.addAttribute("newUser",new User());
        return "user/clientChangeEmail";
    }

    @PostMapping("user/changeEmail")
    public String clientChangeEmail(@ModelAttribute User newUser,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = clientService.findUserByEmail(auth.getName());
        clientService.changeEmail(currentUser,newUser.getEmail());
        model.addAttribute("user",currentUser);
        return "user/clientModifyProfile";
    }


}
