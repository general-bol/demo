package com.example.demo.model;

import com.example.demo.view.ViewEntreprise;
import com.example.demo.view.ViewUser;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ViewUser.class, ViewEntreprise.class})
    private Integer id;

    @JsonView({ViewUser.class, ViewEntreprise.class})
    private String nom;
    @JsonView({ViewUser.class, ViewEntreprise.class})
    private String prenom;


    @JsonView({ViewUser.class, ViewEntreprise.class})
    private String email;

    private String password;

    @JsonView( ViewUser.class )
    private String imageName;

    @JsonView({ViewUser.class, ViewEntreprise.class})
    @ManyToOne
    private Role role;

    @ManyToOne
    @JsonView(ViewUser.class)
    private Pays pays;

    @ManyToOne
    @JsonView(ViewUser.class)
    private Entreprise entreprise;

    @ManyToMany
    @JoinTable(name = "user_job",
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<Job> emploiSet = new HashSet<>();

    @CreationTimestamp
    @JsonView(ViewUser.class)
    private LocalDate createdDate;

    @UpdateTimestamp
    @JsonView(ViewUser.class)
    private LocalDateTime updatedDate;

}