package com.example.demo.controller;

import com.example.demo.dao.JobDao;
import com.example.demo.model.Job;
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
public class JobController {
    @Autowired
    private JobDao jobDao;

    @GetMapping("/job")
    @JsonView(ViewUser.class)
    public List<Job> getJobLst(){

        return jobDao.findAll();

    }

    @GetMapping("/job/{id}")
    @JsonView(ViewUser.class)
    public  ResponseEntity<Job> getJobBYId(@PathVariable int id){
        Optional<Job> optional = jobDao.findById(id);
        if (optional.isPresent())
            return new ResponseEntity<>(optional.get(),HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin/job")
    @JsonView(ViewUser.class)
    public ResponseEntity<Job> ajoutJob(@RequestBody Job user){

        // si un user avec un id est fourni
        if (user.getId() != null){
            Optional<Job> optional = jobDao.findById((user.getId()));
            // et qu'un enregistrement correspond à cet id alors update
            if (optional.isPresent()){
                jobDao.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            // sinon erreur
            return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);

        }
        // sinon create user
        jobDao.save(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);

    }

    @DeleteMapping("/adminjob/{id}")
    @JsonView(ViewUser.class)
    public ResponseEntity<Job> deleteJob(@PathVariable int id){

        Optional<Job> user = jobDao.findById(id);

        if (user.isPresent()) {
            jobDao.deleteById(id);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
