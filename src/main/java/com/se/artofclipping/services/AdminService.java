package com.se.artofclipping.services;

import com.se.artofclipping.model.User;

import java.util.List;

public interface AdminService extends ClientService {

    void saveHairdresser(User hairdresser);
    //boolean delHairdresser(User hairdresser, String adminPassword, String adminEmail);
    List<User> listHairdressers();
    User findUserByEmail(String email);
}
