package com.example.demo.security;

import com.example.demo.dao.UtilisateurDao;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MyUserDetailService myUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");

        if(jwt != null && jwt.startsWith("Bearer ")){
            String token = jwt.substring(7);

            if (jwtUtils.isTokenValide(token)){
                Claims data = jwtUtils.getData(token);


                MyUserDetails myUserDetails = myUserDetailService.loadUserByUsername(data.getSubject());

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
