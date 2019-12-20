package com.se.artofclipping.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Role {

    @Id
    private Long id;
    private String role;
}
