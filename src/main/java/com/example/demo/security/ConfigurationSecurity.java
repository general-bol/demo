package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@EnableWebSecurity
public class ConfigurationSecurity extends WebSecurityConfigurerAdapter {

    // pour authentification BDD
//    @Autowired
//    private DataSource dataSource;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // authentification par jwt

        auth.userDetailsService(myUserDetailService);

        // authentification via la bdd
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("SELECT email, password, 1 FROM utilisateur WHERE email = ?")
//                .authoritiesByUsernameQuery("SELECT email, IF(admin,'ROLE_ADMINISTRATEUR','ROLE_UTILISATEUR') FROM utilisateur WHERE email =?");

        // authentification en mémoire serveur
//        auth
//                .inMemoryAuthentication()
//                .withUser("gerald")
//                .password("root")
//                .roles("UTILISATEUR")
//                .and()
//                .withUser("admin")
//                .password("azerty")
//                .roles("ADMINISTRATEUR");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(httpServletRequest -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.applyPermitDefaultValues();
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
            corsConfiguration.setAllowedHeaders(
                    Arrays.asList("X-Requested-With", "Origin", "Content-Type",
                            "Accept", "Authorization","Access-Control-Allow-Origin"));
            return corsConfiguration;
        }).and()
                .csrf().disable()   // desactive la protection contre la faille Cross-Site Request Forgery
                .authorizeRequests()
                .antMatchers("/connection", "/inscription").permitAll()
                .antMatchers("/admin/**").hasRole("ADMINISTRATEUR")
                .antMatchers("/**").hasAnyRole("ADMINISTRATEUR","UTILISATEUR")

                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder CreationPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
