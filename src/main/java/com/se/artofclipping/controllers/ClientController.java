package com.se.artofclipping.controllers;

import com.se.artofclipping.model.User;
import com.se.artofclipping.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "user/userChangeName";
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
        return "user/userChangeSurname";
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
        return "user/userChangeEmail";
    }

    @PostMapping("user/changeEmail")
    public String clientChangeEmail(@ModelAttribute User newUser,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = clientService.findUserByEmail(auth.getName());

        User userExists = clientService.findUserByEmail(newUser.getEmail());
        if (userExists != null) {
            model.addAttribute("user",currentUser);
            model.addAttribute("newUser",new User());
            return "user/userChangeEmail";
        }

        clientService.changeEmail(currentUser,newUser.getPassword(),newUser.getEmail());

        if(!currentUser.getEmail().equals(newUser.getEmail()) )
        {
            model.addAttribute("user",currentUser);
            model.addAttribute("newUser",new User());
            return "user/userChangeEmail";
        }
        Authentication result = new UsernamePasswordAuthenticationToken(currentUser.getEmail(), currentUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(result);

        model.addAttribute("user",currentUser);
        return "user/loginForm";
    }

    @GetMapping("user/changePasswordView")
    public String clientChangePasswordView(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = clientService.findUserByEmail(auth.getName());
        model.addAttribute("user",user);
        model.addAttribute("newUser",new User());
        return "user/userChangePassword";
    }

    @PostMapping("user/changePassword")
    public String clientChangePassword(@ModelAttribute User newUser,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = clientService.findUserByEmail(auth.getName());
        String oldPassword = newUser.getName();

        if(!clientService.changePassword(currentUser,oldPassword,newUser.getPassword())){
            model.addAttribute("user",currentUser);
            model.addAttribute("newUser",new User());
            return "user/userChangePassword";
        }

        Authentication result = new UsernamePasswordAuthenticationToken(currentUser.getEmail(), currentUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(result);

        model.addAttribute("user",currentUser);
        return "user/loginForm";
    }
}
