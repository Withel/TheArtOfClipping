package com.se.artofclipping.services;

import com.se.artofclipping.model.User;
import com.se.artofclipping.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    protected UserRepository userRepository;
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void changeEmail(User user) {

    }

    @Override
    public void changeName(User user, String name) {

    }

    @Override
    public void changePassword(User user) {

    }

    @Override
    public void changeSurname(User user) {

    }
}
