package com.example.demo.dao;

import com.example.demo.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseDao extends JpaRepository <Entreprise, Integer> {


}
