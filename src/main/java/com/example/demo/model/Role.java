package com.example.demo.model;

import com.example.demo.view.ViewUser;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ViewUser.class)
    private Integer id;
    @JsonView(ViewUser.class)
    private String nom;

}
