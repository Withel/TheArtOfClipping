package com.se.artofclipping.services;

import com.se.artofclipping.model.User;

public interface GuestService {

    User findUserByEmail(String email);
    void saveUser(User user);
}
