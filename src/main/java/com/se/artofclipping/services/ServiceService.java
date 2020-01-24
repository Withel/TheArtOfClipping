package com.se.artofclipping.services;

import com.se.artofclipping.model.Service;

import java.nio.DoubleBuffer;
import java.util.List;

public interface ServiceService {

    List<Service> listService(Character type);
    List<Service> listServiceByTypeAndavailability(Character type, Boolean isAvailable);
    Service findById(Long id);
    void addService();
    void changeDuration(Service service, Integer newName);
    void changeType(Service service, Character newType);
    void changeName(Service service, String newName);
    void changePrice(Service service, Double newPrice);
    void deprecateService(Long id);
}
