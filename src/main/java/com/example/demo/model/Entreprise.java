package com.example.demo.model;

import com.example.demo.view.ViewEntreprise;
import com.example.demo.view.ViewUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ViewUser.class, ViewEntreprise.class})
    private Integer id;
    @JsonView({ViewUser.class, ViewEntreprise.class})
    private String nom;

    @OneToMany(mappedBy = "entreprise")
    @JsonView(ViewEntreprise.class)
    private Set<Utilisateur> listEmploye = new HashSet<>();
}
