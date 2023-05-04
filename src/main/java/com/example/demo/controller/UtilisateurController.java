package com.example.demo.controller;

import com.example.demo.dao.UtilisateurDao;
import com.example.demo.model.Role;
import com.example.demo.model.Utilisateur;
import com.example.demo.security.JwtUtils;
import com.example.demo.service.FileService;
import com.example.demo.view.ViewUser;
import com.fasterxml.jackson.annotation.JsonView;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@RestController
@CrossOrigin
public class UtilisateurController {
    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    FileService fileService;

    @GetMapping("/utilisateurs")
    @JsonView(ViewUser.class)
    public List<Utilisateur> getUtilisateurs(){

        List<Utilisateur> listeUtilisateur = utilisateurDao.findAll();
        return listeUtilisateur;

    }

    @GetMapping("/utilisateur/{id}")
    @JsonView(ViewUser.class)
    public  ResponseEntity<Utilisateur> getUtilisateurBYId(@PathVariable int id){
        Optional<Utilisateur> optional = utilisateurDao.findById(id);
        if (optional.isPresent())
            return new ResponseEntity<>(optional.get(),HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/admin/utilisateur")
    @JsonView(ViewUser.class)
    public ResponseEntity<Utilisateur> ajoutUtilisateur(@RequestPart("user") Utilisateur user,
                                                        @Nullable @RequestParam("file")MultipartFile file){

        // si un user avec un id est fourni
        if (user.getId() != null){
            Optional<Utilisateur> optional = utilisateurDao.findById((user.getId()));
            // et qu'un enregistrement correspond à cet id alors update
            if (optional.isPresent()){

                Utilisateur userToUpdate = optional.get();
                userToUpdate.setNom(user.getNom());
                userToUpdate.setPrenom(user.getPrenom());
                userToUpdate.setEmail(user.getEmail());

                utilisateurDao.save(userToUpdate);

//                if (file != null) {
//                    try {
//                        fileService.transferToLocalFolder(file,"newname");
//                    } catch (IOException e) {
//                        return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
//                    }
//                }

                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            // sinon erreur
            return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);

        }
        // sinon create user
        Role role = new Role();
        role.setId(2);
        user.setRole(role);

        String defaultPassword = passwordEncoder.encode("root");
        user.setPassword(defaultPassword);

        if (file != null) {
            try {
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                fileService.transferToLocalFolder(file,filename);
                user.setImageName(filename);
            } catch (IOException e) {
                return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        utilisateurDao.save(user);

        return new ResponseEntity<>(user,HttpStatus.CREATED);

    }

    @DeleteMapping("/admin/utilisateur/{id}")
    @JsonView(ViewUser.class)
    public ResponseEntity<Utilisateur> deleteUtilisateur(@PathVariable int id){

        Optional<Utilisateur> user = utilisateurDao.findById(id);

        if (user.isPresent()) {
            utilisateurDao.deleteById(id);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/profil")
    @JsonView(ViewUser.class)
    public ResponseEntity<Utilisateur> getProfil(@RequestHeader("Authorization") String bearer){
        String jwt = bearer.substring(7);
        Claims donnees = jwtUtils.getData(jwt);
        Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(donnees.getSubject());

        if (utilisateur.isPresent()){
            return  new ResponseEntity<>(utilisateur.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/image-profil/{userId}")
    public ResponseEntity<byte[]> getImageProfil(@PathVariable int userId){
        Optional<Utilisateur> optional = utilisateurDao.findById(userId);
        if (optional.isPresent()) {
            String imageName = optional.get().getImageName();

            try {
                byte[] image = fileService.getImageByName(imageName);
                HttpHeaders header = new HttpHeaders();
                String mimeType = Files.probeContentType(new File(imageName).toPath());
                header.setContentType(MediaType.valueOf(mimeType));

                return new ResponseEntity<>(image, header, HttpStatus.OK);

            } catch (FileNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }catch ( IOException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
