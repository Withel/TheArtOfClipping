package com.se.artofclipping.services;

import com.se.artofclipping.model.User;

public interface UserService {

    void changeEmail(User user, String email);
    void changeName(User user, String name);
    void changePassword(User user, String password);
    void changeSurname(User user, String surname);
}
