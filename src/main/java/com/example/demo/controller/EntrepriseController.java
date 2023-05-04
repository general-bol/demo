package com.example.demo.controller;

import com.example.demo.dao.EntrepriseDao;
import com.example.demo.model.Entreprise;
import com.example.demo.view.ViewEntreprise;
import com.example.demo.view.ViewUser;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntrepriseController {
    @Autowired
    private EntrepriseDao entrepriseDao;

    @GetMapping("/entreprises")
    @JsonView(ViewEntreprise.class)
    public List<Entreprise> listeEntreprise (){
        return entrepriseDao.findAll();
    }

    @GetMapping("/entreprise/{id}")
    @JsonView(ViewEntreprise.class)
    public Entreprise getEntreprise(@PathVariable int id){
        return entrepriseDao.findById(id).orElse(null);
    }

    @DeleteMapping("/admin/entreprise/{id}")
    @JsonView(ViewEntreprise.class)
    public boolean deleteEntreprise(@PathVariable int id){
        entrepriseDao.deleteById(id);
        return true;
    }

    @PostMapping("/admin/entreprise")
    @JsonView(ViewEntreprise.class)
    public Entreprise createEntreprise(@RequestBody Entreprise newEntreprise){
        entrepriseDao.save(newEntreprise);
        return newEntreprise;
    }
}
