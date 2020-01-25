package com.se.artofclipping.services;

import com.se.artofclipping.model.User;
import com.se.artofclipping.repositories.RoleRepository;
import com.se.artofclipping.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    protected UserRepository userRepository;
    protected BCryptPasswordEncoder bCryptPasswordEncoder;
    protected RoleRepository roleRepository;


    public UserServiceImpl() {

    }

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;

    }


    @Override
    public void changeEmail(User user, String password,String email) {
        if(!email.equals("")) {
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                user.setEmail(email);
                userRepository.save(user);
            }
        }
    }

    @Override
    public void changeName(User user, String name) {
        if(!name.equals("")) {
            user.setName(name);
            userRepository.save(user);
        }
    }

    @Override
    public boolean changePassword(User user, String oldPassword,String newPassword) {
        if(newPassword.equals(""))
        {
            return false;
        }
        if(bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void changeSurname(User user,String surname) {
        if(!surname.equals("")) {
            user.setSurname(surname);
            userRepository.save(user);
        }
    }
}
