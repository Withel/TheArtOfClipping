package com.se.artofclipping.controllers;

import com.se.artofclipping.model.Service;
import com.se.artofclipping.model.User;
import com.se.artofclipping.services.AdminService;
import com.se.artofclipping.services.ServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class AdminController {

    AdminService adminService;
    ServiceService serviceService;

    public AdminController(AdminService adminService, ServiceService serviceService) {
        this.adminService = adminService;
        this.serviceService = serviceService;
    }

    @GetMapping("user/admin/adminpage")
    public String adminPage(){
        return "user/admin/adminpage";
    }

    @GetMapping("user/admin/manage")
    public String adminManageHds(Model model){
        return "user/admin/adminManageHairdressers";
    }

    @GetMapping("user/admin/addhairdresser")
    public String addHairdresser(Model model){
        model.addAttribute("user", new User());
        return "user/admin/adminAddHairdresser";
    }

    @GetMapping("user/admin/manageservices")
    public String manageServices(Model model){

        List<Service> services = new ArrayList<>();

        serviceService.listService('F').iterator().forEachRemaining(services::add);
        serviceService.listService('M').iterator().forEachRemaining(services::add);

        model.addAttribute("services", services);

        return "user/admin/manageServices";
    }

    @GetMapping("/user/admin/manageservices/service/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting id: " + id);

        serviceService.deprecateService(Long.valueOf(id));
        return "redirect:/user/admin/manageservices";
    }

    @PostMapping("admin/register")
    public String register(@ModelAttribute User user, Model model){
        log.debug(user.getEmail());
        User userExists = adminService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            model.addAttribute("user", new User());
            return "user/admin/adminAddHairdresser";
        }
        else if(user.getEmail().isEmpty() || user.getPassword().isEmpty() ||
        user.getName().isEmpty() || user.getSurname().isEmpty())
        {
            model.addAttribute("user", new User());
            return "user/admin/adminAddHairdresser";
        }

        adminService.saveHairdresser(user);
        model.addAttribute("user", new User());

        log.debug("REGISTERED SUCCESSFULLY FFS");

        return "user/admin/adminpage";
    }

    @GetMapping("user/admin/delhairdresser")
    public String delHairdresser(Model model){
        model.addAttribute("listHds",adminService.listHairdressers());
        model.addAttribute("user", new User());
        return "user/admin/adminDelHairdresser";
    }

    @PostMapping("user/admin/delete")
    public String delete(@ModelAttribute User user, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userExists = adminService.findUserByEmail(user.getEmail());
        if (userExists == null) {
            model.addAttribute("listHds",adminService.listHairdressers());
            return "user/admin/adminDelHairdresser";
        }

        else if(!adminService.delHairdresser(userExists,user.getPassword(),auth.getName()))
        {
            model.addAttribute("listHds",adminService.listHairdressers());
            return "user/admin/adminDelHairdresser";
        }
        return "user/admin/adminpage";
    }

    @GetMapping("user/admin/listhairdressers")
    public String list(Model model){
        model.addAttribute("hairdressers", adminService.listHairdressers());

        return "user/admin/adminListHairdressers";
    }

    @GetMapping("user/admin/modify")
    public String adminModify(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = adminService.findUserByEmail(auth.getName());
        model.addAttribute("user",user);
        return "user/admin/adminModifyProfile";
    }


    @GetMapping("user/admin/changeEmailView")
    public String adminChangeEmailView(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = adminService.findUserByEmail(auth.getName());
        model.addAttribute("user",user);
        model.addAttribute("newUser",new User());
        return "user/admin/adminChangeEmail";
    }

    @PostMapping("user/admin/changeEmail")
    public String adminChangeEmail(@ModelAttribute User newUser,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = adminService.findUserByEmail(auth.getName());

        User userExists = adminService.findUserByEmail(newUser.getEmail());
        if (userExists != null) {
            model.addAttribute("user",currentUser);
            model.addAttribute("newUser",new User());
            return "user/admin/adminChangeEmail";
        }

        adminService.changeEmail(currentUser,newUser.getPassword(),newUser.getEmail());

        if(!currentUser.getEmail().equals(newUser.getEmail()) )
        {
            model.addAttribute("user",currentUser);
            model.addAttribute("newUser",new User());
            return "user/admin/adminChangeEmail";
        }
        Authentication result = new UsernamePasswordAuthenticationToken(currentUser.getEmail(), currentUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(result);

        model.addAttribute("user",currentUser);
        return "user/loginForm";
    }

    @GetMapping("user/admin/changePasswordView")
    public String adminChangePasswordView(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = adminService.findUserByEmail(auth.getName());
        model.addAttribute("user",user);
        model.addAttribute("newUser",new User());
        return "user/admin/adminChangePassword";
    }

    @PostMapping("user/admin/changePassword")
    public String adminChangePassword(@ModelAttribute User newUser,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = adminService.findUserByEmail(auth.getName());
        String oldPassword = newUser.getName();

        if(!adminService.changePassword(currentUser,oldPassword,newUser.getPassword())){
            model.addAttribute("user",currentUser);
            model.addAttribute("newUser",new User());
            return "user/admin/adminChangePassword";
        }

        Authentication result = new UsernamePasswordAuthenticationToken(currentUser.getEmail(), currentUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(result);

        model.addAttribute("user",currentUser);
        return "user/loginForm";
    }

    @GetMapping("user/admin/changeHdsNameView")
    public String adminChangeHdsNameView(Model model){
        model.addAttribute("newUser",new User());
        model.addAttribute("listHds",adminService.listHairdressers());
        return "user/admin/adminChangeHdsName";
    }

    @PostMapping("user/admin/changeHdsName")
    public String adminChangeHdsName(@ModelAttribute User user,Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userExists = adminService.findUserByEmail(user.getEmail());
        if (userExists == null) {
            model.addAttribute("newUser",new User());
            model.addAttribute("listHds",adminService.listHairdressers());
            return "user/admin/adminChangeHdsName";
        }

        else if(!adminService.changeHdsName(userExists,user.getPassword(),auth.getName(),user.getName()))
        {
            model.addAttribute("newUser",new User());
            model.addAttribute("listHds",adminService.listHairdressers());
            return "user/admin/adminChangeHdsName";
        }
        return "user/admin/adminpage";
    }

    @GetMapping("user/admin/changeHdsSurnameView")
    public String adminChangeHdsSurnameView(Model model){
        model.addAttribute("newUser",new User());
        model.addAttribute("listHds",adminService.listHairdressers());
        return "user/admin/adminChangeHdsSurname";
    }

    @PostMapping("user/admin/changeHdsSurname")
    public String adminChangeHdsSurname(@ModelAttribute User user,Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userExists = adminService.findUserByEmail(user.getEmail());
        if (userExists == null) {
            model.addAttribute("newUser",new User());
            model.addAttribute("listHds",adminService.listHairdressers());
            return "user/admin/adminChangeHdsSurname";
        }

        else if(!adminService.changeHdsSurname(userExists,user.getPassword(),auth.getName(),user.getSurname()))
        {
            model.addAttribute("newUser",new User());
            model.addAttribute("listHds",adminService.listHairdressers());
            return "user/admin/adminChangeHdsSurname";
        }
        return "user/admin/adminpage";
    }

    @GetMapping("user/admin/changeHdsEmailView")
    public String adminChangeHdsEmailView(Model model){
        model.addAttribute("newUser",new User());
        model.addAttribute("listHds",adminService.listHairdressers());
        return "user/admin/adminChangeHdsEmail";
    }

    @PostMapping("user/admin/changeHdsEmail")
    public String adminChangeHdsEmail(@ModelAttribute User user,Model model) {

        //TODO
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User userExists = adminService.findUserByEmail(user.getEmail());
//        if (userExists == null) {
//            model.addAttribute("newUser",new User());
//            model.addAttribute("listHds",adminService.listHairdressers());
//            return "user/admin/adminChangeHdsEmail";
//        }
//
//        else if(!adminService.changeHdsSurname(userExists,user.getPassword(),auth.getName(),user.getSurname()))
//        {
//            model.addAttribute("newUser",new User());
//            model.addAttribute("listHds",adminService.listHairdressers());
//            return "user/admin/adminChangeHdsEmail";
//        }
        return "user/admin/adminpage";
    }

    @GetMapping("user/admin/changeHdsPasswordView")
    public String adminChangeHdsPasswordView(Model model){
        model.addAttribute("newUser",new User());
        model.addAttribute("listHds",adminService.listHairdressers());
        return "user/admin/adminChangeHdsPassword";
    }

    @PostMapping("user/admin/changeHdsPassword")
    public String adminChangeHdsPassword(@ModelAttribute User user,Model model) {

        //TODO
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User userExists = adminService.findUserByEmail(user.getEmail());
//        if (userExists == null) {
//            model.addAttribute("newUser",new User());
//            model.addAttribute("listHds",adminService.listHairdressers());
//            return "user/admin/adminChangeHdsEmail";
//        }
//
//        else if(!adminService.changeHdsSurname(userExists,user.getPassword(),auth.getName(),user.getSurname()))
//        {
//            model.addAttribute("newUser",new User());
//            model.addAttribute("listHds",adminService.listHairdressers());
//            return "user/admin/adminChangeHdsEmail";
//        }
        return "user/admin/adminpage";
    }

}

