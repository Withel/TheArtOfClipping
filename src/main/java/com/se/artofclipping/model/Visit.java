package com.se.artofclipping.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "visits")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@TODO change the type of that field
    private Boolean isAvailable;

    @ManyToOne
    private User client;

    @ManyToOne
    private User hairDresser;


    //@TODO Discuss this
    @ManyToOne
    private Session session;

    private String day;

    private String time;

    @ManyToOne
    private Service service;
}
