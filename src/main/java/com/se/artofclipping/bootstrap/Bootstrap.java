package com.se.artofclipping.bootstrap;

import com.se.artofclipping.model.Role;
import com.se.artofclipping.model.Service;
import com.se.artofclipping.model.User;
import com.se.artofclipping.model.Visit;
import com.se.artofclipping.repositories.RoleRepository;
import com.se.artofclipping.repositories.ServiceRepository;
import com.se.artofclipping.repositories.UserRepository;
import com.se.artofclipping.repositories.VisitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final VisitRepository visitRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Bootstrap(RoleRepository roleRepository, UserRepository userRepository, ServiceRepository serviceRepository,
                     VisitRepository visitRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.visitRepository = visitRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("Loading bootstrap data.");
        roleRepository.saveAll(getRoles());
        userRepository.saveAll(getAdmins());
        userRepository.saveAll(getCustomers());
        userRepository.saveAll(getHairdressers());
        serviceRepository.saveAll(getServices());
        visitRepository.saveAll(getVisits());
    }

    //@TODO for testing purposes remove later
    private List<Visit> getVisits() {

        List<Visit> visits = new ArrayList<>();

        Visit visit = new Visit();
//        visit.setClient(new User());
//        visit.setHairDresser(new User());
        visit.setIsAvailable(true);
        visit.setDay("12-12-12");
        visit.setTime("10:30");

        visits.add(visit);

        return visits;
    }

    private List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();

        Role adminRole = new Role();
        adminRole.setId(1L);
        adminRole.setRole("ADMIN");

        roles.add(adminRole);

        Role employeeRole = new Role();
        employeeRole.setRole("EMPLOYEE");
        employeeRole.setId(2L);

        roles.add(employeeRole);

        Role customerRole = new Role();
        customerRole.setRole("CUSTOMER");
        customerRole.setId(3L);

        roles.add(customerRole);

        return roles;
    }

    private List<User> getAdmins() {
        List<User> admins = new ArrayList<>();

        User admin = new User();
        admin.setEmail("admin@admin.com");
        admin.setPassword(bCryptPasswordEncoder.encode("admin"));
        admin.setActive(1);
        admin.setId(1L);
        admin.setName("AdminName");
        admin.setSurname("AdminSurname");

        Role userRole = roleRepository.findByRole("ADMIN");
        admin.setRoles(new HashSet<>(Arrays.asList(userRole)));

        admins.add(admin);

        return admins;
    }

    private List<User> getCustomers() {
        List<User> customers = new ArrayList<>();

        User customer = new User();
        customer.setEmail("aaa@aaa.com");
        customer.setPassword(bCryptPasswordEncoder.encode("aaa"));
        customer.setActive(1);
        customer.setName("aaa");
        customer.setSurname("aaa");

        Role userRole = roleRepository.findByRole("CUSTOMER");
        customer.setRoles(new HashSet<>(Arrays.asList(userRole)));

        customers.add(customer);

        return customers;
    }

    private List<User> getHairdressers() {
        List<User> hairdressers = new ArrayList<>();

        User hairdresser = new User();
        hairdresser.setEmail("hhh@hhh.com");
        hairdresser.setPassword(bCryptPasswordEncoder.encode("hhh"));
        hairdresser.setActive(1);
        hairdresser.setName("hhh");
        hairdresser.setSurname("hhh");

        Role userRole = roleRepository.findByRole("EMPLOYEE");
        hairdresser.setRoles(new HashSet<>(Arrays.asList(userRole)));

        hairdressers.add(hairdresser);

        hairdresser = new User();
        hairdresser.setEmail("ggg@ggg.com");
        hairdresser.setPassword(bCryptPasswordEncoder.encode("ggg"));
        hairdresser.setActive(1);
        hairdresser.setName("ggg");
        hairdresser.setSurname("ggg");

        hairdresser.setRoles(new HashSet<>(Arrays.asList(userRole)));

        hairdressers.add(hairdresser);

        return hairdressers;
    }

    private List<Service> getServices() {
        List<Service> services = new ArrayList<>();

        // Male services
        Service service = new Service();
        service.setName("Clipper Cut");
        service.setIsActive(true);
        service.setDurationMinutes(15);
        service.setPrice(18D);
        service.setType(Character.toUpperCase('M'));
        services.add(service);

        service = new Service();
        service.setName("Scissor Cut");
        service.setIsActive(true);
        service.setDurationMinutes(30);
        service.setPrice(20D);
        service.setType(Character.toUpperCase('M'));
        services.add(service);

        service = new Service();
        service.setName("Cut & Beard Trim/Wash");
        service.setIsActive(true);
        service.setDurationMinutes(45);
        service.setPrice(25D);
        service.setType(Character.toUpperCase('M'));
        services.add(service);

        service = new Service();
        service.setName("Cut & Beard Trim & Wash");
        service.setIsActive(true);
        service.setDurationMinutes(60);
        service.setPrice(30D);
        service.setType(Character.toUpperCase('M'));
        services.add(service);

        service = new Service();
        service.setName("Cut & Style");
        service.setIsActive(true);
        service.setDurationMinutes(90);
        service.setPrice(60D);
        service.setType(Character.toUpperCase('M'));
        services.add(service);

        service = new Service();
        service.setName("Cut & Wash & Style");
        service.setIsActive(true);
        service.setDurationMinutes(90);
        service.setPrice(70D);
        service.setType(Character.toUpperCase('M'));
        services.add(service);

        // Female services
        service = new Service();
        service.setName("Bang Trim");
        service.setIsActive(true);
        service.setDurationMinutes(15);
        service.setPrice(10D);
        service.setType(Character.toUpperCase('F'));
        services.add(service);

        service = new Service();
        service.setName("Cut Only");
        service.setIsActive(true);
        service.setDurationMinutes(30);
        service.setPrice(20D);
        service.setType(Character.toUpperCase('F'));
        services.add(service);

        service = new Service();
        service.setName("Wash & Style");
        service.setIsActive(true);
        service.setDurationMinutes(45);
        service.setPrice(35D);
        service.setType(Character.toUpperCase('F'));
        services.add(service);

        service = new Service();
        service.setName("Wash & Style");
        service.setIsActive(true);
        service.setDurationMinutes(45);
        service.setPrice(35D);
        service.setType(Character.toUpperCase('F'));
        services.add(service);

        service = new Service();
        service.setName("Wash & Cut");
        service.setIsActive(true);
        service.setDurationMinutes(45);
        service.setPrice(40D);
        service.setType(Character.toUpperCase('F'));
        services.add(service);

        service = new Service();
        service.setName("Wash & Cut & Style");
        service.setIsActive(true);
        service.setDurationMinutes(60);
        service.setPrice(70D);
        service.setType(Character.toUpperCase('F'));
        services.add(service);

        service = new Service();
        service.setName("Wash & Cut & Color");
        service.setIsActive(true);
        service.setDurationMinutes(60);
        service.setPrice(90D);
        service.setType(Character.toUpperCase('F'));
        services.add(service);

        return services;
    }
}
