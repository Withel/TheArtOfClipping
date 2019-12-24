package com.se.artofclipping.services;

import com.se.artofclipping.model.User;

import java.util.List;

public interface AdminService extends ClientService {

    void saveHairdresser(User hairdresser);
    List<User> listHairdressers();
    User findUserByEmail(String email);
}
