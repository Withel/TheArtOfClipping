package com.se.artofclipping.services;

import com.se.artofclipping.model.Service;

import java.util.List;

public interface ServiceService {

    List<Service> listService(Character type);
    void addService();
    void changeDuration(Service service);
    void changeName(Service service);
    void changePrice(Service service);
    void deprecateService(Service service);
}
