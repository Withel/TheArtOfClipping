package com.se.artofclipping.controllers;

import com.se.artofclipping.services.ServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    ServiceService serviceService;

    public IndexController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping({"", "/", "/index"})
    public String home(Model model){
        model.addAttribute("servicesF", serviceService.listService('F'));
        model.addAttribute("servicesM", serviceService.listService('M'));
        return "index";
    }
}
