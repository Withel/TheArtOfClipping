package com.se.artofclipping.controllers;

import com.se.artofclipping.model.Visit;
import com.se.artofclipping.services.AdminService;
import com.se.artofclipping.services.ServiceService;
import com.se.artofclipping.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VisitController {

    ServiceService serviceService;
    //@TODO change it for something relevant
    AdminService adminService;
    VisitService visitService;

    public VisitController(ServiceService serviceService, AdminService adminService, VisitService visitService) {
        this.serviceService = serviceService;
        this.adminService = adminService;
        this.visitService = visitService;
    }

    @GetMapping("rsvr/{id}")
    public String showAvailable(@PathVariable String id, Model model){

        model.addAttribute("service", serviceService.findById(Long.parseLong(id)));

        return "visit1";
    }

    @GetMapping("rsvr/{id}/{day}")
    public String day(@PathVariable String id,
                      @PathVariable String day, Model model){

        model.addAttribute("service", serviceService.findById(Long.parseLong(id)));
        model.addAttribute("hairdressers", adminService.listHairdressers());

        //TODO this is just ridiculous
        List<String> times = new ArrayList<>();
        times.add("10:00");
        times.add("10:30");
        times.add("11:00");
        times.add("11:30");
        times.add("12:00");
        times.add("12:30");

        List<String> toRemove = new ArrayList<>();

        for(String time : times){
            for(Visit visit : visitService.listVisits())
            if(time.equals(visit.getTime())){
                toRemove.add(time);
            }
        }

        times.removeAll(toRemove);

        model.addAttribute("visits", visitService.listVisits());
        model.addAttribute("times", times);

        return "visit2";
    }
}
