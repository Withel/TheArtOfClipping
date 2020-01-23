package com.se.artofclipping.services;

import com.se.artofclipping.model.Service;
import com.se.artofclipping.repositories.ServiceRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<Service> listService(Character type) {
        List<Service> services = new ArrayList<>();

        //@TODO list only active services
//        serviceRepository.findAll().iterator().forEachRemaining(services::add);
        serviceRepository.findAllByType(type).iterator().forEachRemaining(services::add);

        return services;
    }

    @Override
    public Service findById(Long id) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);

        if(!serviceOptional.isPresent()){
            throw new RuntimeException("Service Not Found");
        }

        return serviceOptional.get();
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

    @Transactional
    @Override
    public void deprecateService(Long id) {
        Service serviceToDeprecate = findById(id);

        serviceToDeprecate.setIsActive(false);
        serviceRepository.save(serviceToDeprecate);
    }
}
