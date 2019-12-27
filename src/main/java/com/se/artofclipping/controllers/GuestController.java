package com.se.artofclipping.controllers;

import com.se.artofclipping.model.User;
import com.se.artofclipping.services.GuestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class GuestController {

    //@Autowired
    private GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    //@TODO change this name to smething relevant
    @GetMapping("user")
    public String goTo(){
        //@TODO change it for query with admin role
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName().equals("admin@admin.com")){
            return "admin/adminpage";
        } else{
            return "user/user";
        }
    }

    @GetMapping("login")
    public String login(){
        return "user/loginForm";
    }

    @GetMapping("register")
    public String newUser(Model model){
        model.addAttribute("user", new User());

        return "user/registerForm";
    }

    @PostMapping("registration")
    public String createNewUser(@ModelAttribute User user, BindingResult bindingResult,
                                      Model model) {
        log.debug(user.getEmail());
        User userExists = guestService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            guestService.saveUser(user);
            model.addAttribute("user", new User());
        }
        log.debug("REGISTERED SUCCESSFULLY FFS");

        return "user/registerForm";
    }

    //@TODO remove later
    // function that after loggin redirect us to our "secretpage"

    @RequestMapping("secretpage")
    public String showSecretPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = guestService.findUserByEmail(auth.getName());
        model.addAttribute(user);
        return "secretpage";
    }
}
