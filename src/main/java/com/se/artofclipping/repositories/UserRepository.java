package com.se.artofclipping.repositories;

import com.se.artofclipping.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
