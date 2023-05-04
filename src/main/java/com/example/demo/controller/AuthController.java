package com.example.demo.controller;

import com.example.demo.dao.UtilisateurDao;
import com.example.demo.model.Utilisateur;
import com.example.demo.security.JwtUtils;
import com.example.demo.security.MyUserDetailService;
import com.example.demo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UtilisateurDao utilisateurDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MyUserDetailService myUserDetailService;

    @PostMapping("/connection")
    public ResponseEntity<String> connection(@RequestBody Utilisateur utilisateur){

        MyUserDetails myUserDetail;

        try {

            myUserDetail = (MyUserDetails)authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(utilisateur
                            .getEmail(),
                            utilisateur.getPassword())
            ).getPrincipal();
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        MyUserDetails myUserDetails = (MyUserDetails) myUserDetailService.loadUserByUsername(utilisateur.getEmail());

        return new ResponseEntity<>(jwtUtils.generateJwt(myUserDetails),HttpStatus.OK);
    }

    @PostMapping("/inscription")
    public ResponseEntity<Utilisateur> inscription(@RequestBody Utilisateur utilisateur){

        if (utilisateur.getId() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (utilisateur.getPassword().length() <= 3) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(utilisateur.getEmail()).matches()) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Utilisateur> optional = utilisateurDao.findByEmail(utilisateur.getEmail());

        if (optional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateurDao.save(utilisateur);
        return new ResponseEntity<>(HttpStatus.CREATED);


    }
}
