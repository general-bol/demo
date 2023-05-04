package com.example.demo.security;

import com.example.demo.dao.UtilisateurDao;
import com.example.demo.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Override
    public MyUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Utilisateur> optional = utilisateurDao.findByEmail(email);

        if(optional.isEmpty()) {
            throw new UsernameNotFoundException("user not exists");
        }

        return new MyUserDetails(optional.get());
    }
}
