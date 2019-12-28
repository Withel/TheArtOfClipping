package com.se.artofclipping.services;

import com.se.artofclipping.model.Role;
import com.se.artofclipping.model.User;
import com.se.artofclipping.repositories.RoleRepository;
import com.se.artofclipping.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl extends UserServiceImpl implements AdminService {

    RoleRepository roleRepository;

    public AdminServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                            RoleRepository roleRepository) {
        super(userRepository, bCryptPasswordEncoder);
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveHairdresser(User hairdresser) {
        hairdresser.setPassword(bCryptPasswordEncoder.encode(hairdresser.getPassword()));
        hairdresser.setActive(1);
        Role userRole = roleRepository.findByRole("EMPLOYEE");
        hairdresser.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(hairdresser);
    }

    @Override
    public List<User> listHairdressers() {

        List<User> hairdressers = new ArrayList<>();

        //@TODO make this correct query for finding only hairdressers
        userRepository.findAll().iterator().forEachRemaining(hairdressers::add);

        return hairdressers;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteAccount(User user) {

    }

    @Override
    public void changeEmail(User user,String email) {

    }

    @Override
    public void changeName(User user, String name) {

    }

    @Override
    public void changePassword(User user, String password) {

    }

    @Override
    public void changeSurname(User user,String surname) {

    }
}
