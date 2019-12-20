package com.se.artofclipping.services;

import com.se.artofclipping.model.Role;
import com.se.artofclipping.model.User;
import com.se.artofclipping.repositories.RoleRepository;
import com.se.artofclipping.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

//@TODO probably will have to split this into more interfaces

@Service
public class GuestServiceImpl implements GuestService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public GuestServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //@TODO remove later -> also for testing purposes

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);

    }
}
