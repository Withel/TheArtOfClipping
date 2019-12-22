package com.se.artofclipping.services;

import com.se.artofclipping.model.Service;
import com.se.artofclipping.repositories.ServiceRepository;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<Service> listService(Character type) {
        List<Service> services = new ArrayList<>();

//        serviceRepository.findAll().iterator().forEachRemaining(services::add);
        serviceRepository.findAllByType(type).iterator().forEachRemaining(services::add);

        return services;
    }

    @Override
    public void addService() {

    }

    @Override
    public void changeDuration(Service service) {

    }

    @Override
    public void changeName(Service service) {

    }

    @Override
    public void changePrice(Service service) {

    }

    @Override
    public void deprecateService(Service service) {

    }
}
