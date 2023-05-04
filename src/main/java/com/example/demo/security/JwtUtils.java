package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtils {

    public String generateJwt(MyUserDetails myUserDetails){
        Map<String, Object> dataUser = new HashMap<>();
        dataUser.put("prenom",myUserDetails.getUtilisateur().getPrenom());
        dataUser.put("nom",myUserDetails.getUtilisateur().getNom());
        dataUser.put("role",myUserDetails.getUtilisateur().getRole().getNom());
        //dataUser.put("email",myUserDetails.getUtilisateur().getEmail());

        return Jwts.builder()
                .setClaims(dataUser)
                .setSubject(myUserDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256,"azerty")
                .compact();
    }

    public Claims getData(String jwt){
        return Jwts.parser()
                .setSigningKey("azerty")
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean isTokenValide (String jwt){
        try {
            Claims donnees = getData(jwt);

        } catch(SignatureException e){
            return false;
        }
        return true;
    }
}
