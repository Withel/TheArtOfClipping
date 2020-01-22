package com.se.artofclipping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TempVisit {

    //@TODO change the type of that field
    private Boolean isAvailable;

    private User client;

    private User hairDresser;

    private String day;

    private String time;

    private Service service;

    public Visit convertToVisit(){
        Visit newVisit = new Visit();
        newVisit.setHairDresser(hairDresser);
        newVisit.setClient(client);
        newVisit.setTime(time);
        newVisit.setDay(day);
        newVisit.setService(service);

        return newVisit;
    }
}
