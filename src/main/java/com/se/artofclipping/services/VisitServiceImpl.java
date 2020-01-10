package com.se.artofclipping.services;

import com.se.artofclipping.model.User;
import com.se.artofclipping.model.Visit;
import com.se.artofclipping.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public List<Visit> listVisits() {

        List<Visit> visits = new ArrayList<>();
        visitRepository.findAll().iterator().forEachRemaining(visits::add);

        return visits;
    }

    @Override
    public void addNewVisit(User user, User hd, Long serviceId, String day, String time) {

//        Visit visit = new Visit();

    }

    @Override
    public void addNewVisit(Visit visit) {
        visitRepository.save(visit);
    }

    @Override
    public void deleteVisit(Visit visit) {

    }
}
