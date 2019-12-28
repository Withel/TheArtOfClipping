package com.se.artofclipping.services;

import com.se.artofclipping.model.User;

public interface ClientService extends UserService {
    User findUserByEmail(String email);
    void deleteAccount(User user);
}
