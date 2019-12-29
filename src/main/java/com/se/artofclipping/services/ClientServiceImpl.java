package com.se.artofclipping.services;

import com.se.artofclipping.model.Role;
import com.se.artofclipping.model.User;
import com.se.artofclipping.repositories.RoleRepository;
import com.se.artofclipping.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

//@TODO probably will have to split this into more interfaces

@Service
@Primary
public class ClientServiceImpl implements ClientService {

    protected UserRepository userRepository;
    protected RoleRepository roleRepository;
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ClientServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("CUSTOMER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void deleteAccount(User user) {

    }

    @Override
    public void changeEmail(User user,String email) {

        //TODO
        user.setEmail(email);
        userRepository.save(user);

    }

    @Override
    public void changeName(User user, String name) {
        //TODO check if any new name is given
        user.setName(name);
        userRepository.save(user);
    }

    @Override
    public void changePassword(User user, String password) {

    }

    @Override
    public void changeSurname(User user,String surname) {
        user.setSurname(surname);
        userRepository.save(user);
    }
}
