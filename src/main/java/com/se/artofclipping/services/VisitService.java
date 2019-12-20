package com.se.artofclipping.services;

import com.se.artofclipping.model.Visit;

import java.util.List;

public interface VisitService {

    void addNewVisit(Visit visit);
    void deleteVisit(Visit visit);
    List<Visit> listVisits();
}
