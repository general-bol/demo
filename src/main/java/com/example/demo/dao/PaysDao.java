package com.example.demo.dao;

import com.example.demo.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaysDao extends JpaRepository<Pays, Integer> {
}
