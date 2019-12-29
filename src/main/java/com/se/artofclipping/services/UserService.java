package com.se.artofclipping.services;

import com.se.artofclipping.model.User;

public interface UserService {

    void changeEmail(User user, String password, String email);
    void changeName(User user,String name);
    boolean changePassword(User user, String oldPassword,String newPassword);
    void changeSurname(User user, String surname);
}
