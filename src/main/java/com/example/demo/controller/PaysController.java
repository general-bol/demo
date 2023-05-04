package com.example.demo.controller;

import com.example.demo.dao.PaysDao;
import com.example.demo.model.Pays;
import com.example.demo.view.ViewUser;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PaysController {
    @Autowired
    private PaysDao paysDao;

    @GetMapping("/pays")
    @JsonView(ViewUser.class)
    public List<Pays> getPaysLst(){

        return paysDao.findAll();

    }

    @GetMapping("/pays/{id}")
    @JsonView(ViewUser.class)
    public  ResponseEntity<Pays> getPaysBYId(@PathVariable int id){
        Optional<Pays> optional = paysDao.findById(id);
        if (optional.isPresent())
            return new ResponseEntity<>(optional.get(),HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin/pays")
    @JsonView(ViewUser.class)
    public ResponseEntity<Pays> ajoutPays(@RequestBody Pays user){

        // si un user avec un id est fourni
        if (user.getId() != null){
            Optional<Pays> optional = paysDao.findById((user.getId()));
            // et qu'un enregistrement correspond à cet id alors update
            if (optional.isPresent()){
                paysDao.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            // sinon erreur
            return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);

        }
        // sinon create user
        paysDao.save(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);

    }

    @DeleteMapping("/admin/pays/{id}")
    @JsonView(ViewUser.class)
    public ResponseEntity<Pays> deletePays(@PathVariable int id){

        Optional<Pays> user = paysDao.findById(id);

        if (user.isPresent()) {
            paysDao.deleteById(id);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
