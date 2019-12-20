package com.se.artofclipping.services;

import com.se.artofclipping.model.User;
import com.se.artofclipping.model.Visit;

import java.util.List;

public interface HairdresserService extends UserService {

    List<Visit> listVisits(User user);
}
