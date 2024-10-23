package com.henriquenascimento.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

public class Category extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_CATEGORY", unique = true, nullable = false)
    private Long id;

    @Column(name = "NAME", unique = true)
    private String name;

}
