package com.se.artofclipping.services;

import com.se.artofclipping.model.User;

public interface ClientService extends UserService {

    void deleteAccount(User user);
}
