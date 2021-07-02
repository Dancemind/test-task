package com.example.testtask.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GeologicalClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    private Section section;

}
