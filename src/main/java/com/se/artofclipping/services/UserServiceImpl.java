package com.se.artofclipping.services;

import com.se.artofclipping.model.Role;
import com.se.artofclipping.model.User;
import com.se.artofclipping.repositories.RoleRepository;
import com.se.artofclipping.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    protected UserRepository userRepository;
    protected BCryptPasswordEncoder bCryptPasswordEncoder;
    RoleRepository roleRepository;

    public UserServiceImpl(){

    }

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public void changeEmail(User user, String password,String email) {
       if(bCryptPasswordEncoder.matches(password, user.getPassword())){
            user.setEmail(email);
            userRepository.save(user);
       }
    }

    @Override
    public void changeName(User user, String name) {
        //TODO check if any new name is given
        user.setName(name);
        userRepository.save(user);
    }

    @Override
    public boolean changePassword(User user, String oldPassword,String newPassword) {
        if(bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void changeSurname(User user,String surname) {
        user.setSurname(surname);
        userRepository.save(user);
    }
}
