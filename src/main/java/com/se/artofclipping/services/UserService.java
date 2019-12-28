package com.se.artofclipping.services;

import com.se.artofclipping.model.User;

public interface UserService {

    void changeEmail(User user);
    void changeName(User user, String name);
    void changePassword(User user);
    void changeSurname(User user);
}
