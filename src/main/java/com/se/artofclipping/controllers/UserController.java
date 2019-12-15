package com.se.artofclipping.controllers;

import com.se.artofclipping.model.User;
import com.se.artofclipping.services.UserService;
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

import java.util.HashSet;
import java.util.Set;

@Controller
@Slf4j
public class UserController {

    //@Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //@TODO change this name to smething relevant
    @GetMapping("user")
    public String goTo(){
        return "user/user";
    }

    @GetMapping("login")
    public String login(){
        return "user/loginForm";
    }

    @GetMapping("register")
    public String newUser(Model model){
        model.addAttribute("user", new User());

        //@TODO remove later
        // for testing purposes
        // just printing out every user in database

        Set<User> users = new HashSet<>();

        userService.getUserRepository().findAll().iterator()
                .forEachRemaining(users::add);

        for(User u : users){
            System.out.println(u);
        }

        return "user/registerForm";
    }

    // commented parts of that function are my "old" way of doing that

    @PostMapping("registration")
    public String createNewUser(@ModelAttribute User user, BindingResult bindingResult,
                                      Model model) {
        System.out.println(user.getEmail());
//        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
//            modelAndView.setViewName("registration");
            return "registration";
        } else {
            userService.saveUser(user);
            model.addAttribute("user", new User());
//            modelAndView.addObject("successMessage", "User has been registered successfully");
//            modelAndView.addObject("user", new User());
//            modelAndView.setViewName("user/registerForm");

        }
        log.debug("REGISTERED SUCCESSFULLY FFS");

        // for testing purposes
//        System.out.println(user.getId());
//        System.out.println(user.getEmail());
//        System.out.println(user.getName());
//        System.out.println(user.getPassword());
//        System.out.println(user.getSecondName());


        return "user/registerForm";
    }

    //@TODO remove later
    // function that after loggin redirect us to our "secretpage"

    @RequestMapping("secretpage")
    public String showSecretPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute(user);
        return "secretpage";
    }
}
