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
public class AdminServiceImpl extends ClientServiceImpl implements AdminService {

    public AdminServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                            RoleRepository roleRepository) {
        super(userRepository,roleRepository, bCryptPasswordEncoder);
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
    public boolean delHairdresser(User hairdresser, String adminPassword, String adminEmail) {
       //TODO make successful deletion of hairdressers
        User user = userRepository.findByEmail(adminEmail);
        Role userRole = roleRepository.findByRole("EMPLOYEE");

        if(bCryptPasswordEncoder.matches(adminPassword, user.getPassword()) &&
                hairdresser.getRoles().equals(new HashSet<>(Arrays.asList(userRole)))) {
            hairdresser.setActive(0);
            userRepository.save(hairdresser);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeHdsName(User hairdresser, String adminPassword, String adminEmail, String newName) {
        User user = userRepository.findByEmail(adminEmail);
        Role userRole = roleRepository.findByRole("EMPLOYEE");

        if(bCryptPasswordEncoder.matches(adminPassword, user.getPassword()) &&
                hairdresser.getRoles().equals(new HashSet<>(Arrays.asList(userRole)))) {

            hairdresser.setName(newName);;
            userRepository.save(hairdresser);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeHdsSurname(User hairdresser, String adminPassword, String adminEmail, String newSurname) {
        User user = userRepository.findByEmail(adminEmail);
        Role userRole = roleRepository.findByRole("EMPLOYEE");

        if(bCryptPasswordEncoder.matches(adminPassword, user.getPassword()) &&
                hairdresser.getRoles().equals(new HashSet<>(Arrays.asList(userRole)))) {

            hairdresser.setSurname(newSurname);;
            userRepository.save(hairdresser);
            return true;
        }
        return false;
    }

    @Override
    public List<User> listHairdressers() {

        List<User> hairdressers = new ArrayList<>();
        List<User> users = new ArrayList<>();

        //@TODO make this correct query for finding only hairdressers
        userRepository.findAll().iterator().forEachRemaining(users::add);

        Role userRole = roleRepository.findByRole("EMPLOYEE");

        for(User user : users){
            if(user.getRoles().equals(new HashSet<>(Arrays.asList(userRole))) && user.getActive() == 1)
            {
                hairdressers.add(user);
            }
        }

        return hairdressers;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteAccount(User user) {

    }
}
