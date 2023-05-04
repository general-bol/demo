package com.example.demo.dao;

import com.example.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {

    Utilisateur findByPrenom( String prenom);

    Optional<Utilisateur> findByEmail(String Email);

    @Query("FROM Utilisateur U JOIN U.pays P WHERE P.nom = $1")
    List<Utilisateur> utilisateurFromPays(String pays);
}
