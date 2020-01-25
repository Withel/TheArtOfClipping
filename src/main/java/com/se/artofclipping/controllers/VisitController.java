package com.se.artofclipping.controllers;

import com.se.artofclipping.model.Service;
import com.se.artofclipping.model.TempVisit;
import com.se.artofclipping.model.User;
import com.se.artofclipping.model.Visit;
import com.se.artofclipping.services.AdminService;
import com.se.artofclipping.services.ServiceService;
import com.se.artofclipping.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@SessionAttributes("tempVisit")
public class VisitController {

    ServiceService serviceService;
    //@TODO change it for something relevant
    AdminService adminService;
    VisitService visitService;

    private TempVisit tempVisit;

    public VisitController(ServiceService serviceService, AdminService adminService, VisitService visitService, TempVisit tempVisit) {
        this.serviceService = serviceService;
        this.adminService = adminService;
        this.visitService = visitService;
        this.tempVisit = tempVisit;
    }


    @GetMapping("rsvr/")
    public String showAvailable(@RequestParam String id,
                                @RequestParam String date,
//                                @ModelAttribute User user,
                                Model model){

        Service chosen = serviceService.findById(Long.parseLong(id));

        model.addAttribute("service", chosen);
        model.addAttribute("hairdresser", new User());

        if(id != null) {
            System.out.println(id);
            tempVisit.setService(serviceService.findById(Long.parseLong(id)));
        }

        //@TODO How to write unclean code pt1 at least select works
        User optionUser = new User();
        optionUser.setName("Chose");
        optionUser.setSurname("Hairdresser");
        optionUser.setEmail("optionUser");

        List<User> hdsAndChoseOption = adminService.listHairdressers();
        hdsAndChoseOption.add(0, optionUser);

        if(!date.equals("")) {
            System.out.println(date);
            tempVisit.setDay(date);
            model.addAttribute("listHds", hdsAndChoseOption);
            model.addAttribute("timeVisit", new Visit());
            return "calendar/calendarSitePost";
        } else {
            model.addAttribute("listHds", new ArrayList<>());
        }

        return "calendar/calendarSite";
    }


    @PostMapping("rsvr/chosetime")
    public String time(@ModelAttribute User hairdresser,
                       @ModelAttribute Visit timeVisit,
                       Model model){
        String sDate1 = tempVisit.getDay();
        Date date1;
        try{
            date1 = new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);
        }catch(Exception e){
            return "calendar/calendarSite";
        }

        String date = date1.toString();
        String tab[] = date.split(" ");

        String finalDate= tab[0]+" "+tab[1]+" "+tab[2]+ " "+tab[5];
        model.addAttribute("date", finalDate);

        if(hairdresser.getEmail().equals("optionUser")){
            User optionUser = new User();
            optionUser.setName("Chose");
            optionUser.setSurname("Hairdresser");
            optionUser.setEmail("optionUser");
            tempVisit.setHairDresser(optionUser);
            List<User> hdsAndChoseOption = adminService.listHairdressers();
            hdsAndChoseOption.add(0, optionUser);
            model.addAttribute("listHds", hdsAndChoseOption);
            model.addAttribute("hairdresser", new User());
            model.addAttribute("timeVisit", new Visit());
            return "calendar/calendarSitePost";
        }

        System.out.println("Chosen hairdresser: " + hairdresser.getEmail());
        System.out.println("Chosen time: " + timeVisit.getTime());

        //@TODO WTF IS GOING ON HERE XDDDDD
        List<String> times = new ArrayList<>();
        times.add("10:00");
        times.add("10:30");
        times.add("11:00");
        times.add("11:30");
        times.add("12:00");
        times.add("12:30");
        times.add("13:00");
        times.add("13:30");
        times.add("14:00");
        times.add("14:30");
        times.add("15:00");
        times.add("15:30");
        times.add("16:00");
        times.add("16:30");
        times.add("17:00");
        times.add("17:30");

        List<Visit> availableVisits = visitService.findByDay(tempVisit.getDay());

        System.out.println("ALL VISITS THIS DAY");
        for(Visit visit : availableVisits){
            System.out.println("============================");
            System.out.println(visit.getDay());
            System.out.println(visit.getTime());
            System.out.println(visit.getHairDresser().getEmail());
        }

        List<Visit> visitsToRemove = new ArrayList<>();

        // removing visits for another hairdressers
        for(Visit visit : availableVisits){
            if(!visit.getHairDresser().getEmail().equals(hairdresser.getEmail())){
                visitsToRemove.add(visit);
            }
        }

        availableVisits.removeAll(visitsToRemove);

        System.out.println("VISITS FOR CHOSEN HAIRDRESSER");
        for(Visit visit : availableVisits){
            System.out.println("============================");
            System.out.println(visit.getDay());
            System.out.println(visit.getTime());
        }

        System.out.println("ALL TIMES");
        for(String currentTime : times){
            System.out.println(currentTime);
        }

        List<String> timesToRemove = new ArrayList<>();

        // removing times for current day and hairdresser
        for(String currentTime : times){
            for(Visit visit : availableVisits)
                if(currentTime.equals(visit.getTime())){
                        timesToRemove.add(currentTime);
                }
        }

        times.removeAll(timesToRemove);

        System.out.println("TIMES AVAILABLE FOR CURRENT DAY AND CHOSEN HAIRDRESSER");
        for(String currentTime : times){
            System.out.println(currentTime);
        }

        List<Visit> visitTimesOnly = new ArrayList<>();

        Visit visitTime;

        for(int i=0; i<times.size(); i++){
            visitTime = new Visit();
            visitTime.setTime(times.get(i));
            visitTimesOnly.add(visitTime);
        }


        model.addAttribute("times", times);
//        model.addAttribute("times", visitTimesOnly);
        model.addAttribute("timeVisit", new Visit());

        User hds = adminService.findUserByEmail(hairdresser.getEmail());
        model.addAttribute("hairdresser", hds);

        tempVisit.setHairDresser(hds);

        //@TODO How to write unclean code pt3
        User optionUser = new User();
        optionUser.setName("Chose");
        optionUser.setSurname("Hairdresser");
        optionUser.setEmail("optionUser");

        List<User> hdsAndChoseOption = adminService.listHairdressers();
        hdsAndChoseOption.add(0, optionUser);
        model.addAttribute("listHds", hdsAndChoseOption);

        return "calendar/calendarSitePost";
    }

    @PostMapping("rsvr/timechosen")
    public String timeChosen(@ModelAttribute Visit timeVisit, Model model){

//        if(tempVisit.getHairDresser().getEmail().equals("optionUser")){
////            model.addAttribute("")
//            model.addAttribute("hairdresser", new User());
//            model.addAttribute("timeVisit", new Visit());
//            return "calendar/calendarSitePost";
//        }

        tempVisit.setTime(timeVisit.getTime());

        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmation(Model model){


//        System.out.println(tempVisit.getTemp1());
//        System.out.println(tempVisit.getTemp2());
//        boolean checkIfGood = tempVisit.check();
//        System.out.println(todos.get(1));
//        System.out.println(todos.get(1));
//        System.out.println(todos.get(1));

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = adminService.findUserByEmail(auth.getName());
//        tempVisit.setClient(user);

        Visit newVisit = tempVisit.convertToVisit();

        System.out.println(newVisit.getHairDresser().getEmail());
        System.out.println(newVisit.getDay());
        System.out.println(newVisit.getTime());
        System.out.println(newVisit.getService().getName());
//        System.out.println(newVisit.getClient().getEmail());



        model.addAttribute("visit", newVisit);

//        visitService.addNewVisit(visit);

        return "calendar/confirmation";
    }

    @GetMapping("/37shbdngh8b67sdnas86vb5")
    public String reserve(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = adminService.findUserByEmail(auth.getName());
        tempVisit.setClient(user);

        Visit newVisit = tempVisit.convertToVisit();
        visitService.addNewVisit(newVisit);

        tempVisit.setTime(null);
        tempVisit.setHairDresser(null);
        tempVisit.setService(null);
        tempVisit.setClient(null);
        tempVisit.setDay(null);

        //@TODO change to redirection to user's visits
        return "redirect:/";
    }
}
