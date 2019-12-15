package com.se.artofclipping.bootstrap;

import com.se.artofclipping.model.Role;
import com.se.artofclipping.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RoleBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    public RoleBootstrap(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){
        roleRepository.saveAll(getRoles());
        log.debug("Loading bootstrap data.");
    }

    private List<Role> getRoles(){
        List<Role> roles = new ArrayList<>();

        Role adminRole = new Role();
        adminRole.setId(1L);
        adminRole.setRole("ADMIN");

        roles.add(adminRole);

        return roles;
    }
}
