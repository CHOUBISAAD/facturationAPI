package org.example.appcondidature.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // pour simplifier les tests avec Postman
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated() // toutes les requêtes nécessitent une auth
                )
                .httpBasic(); // utilise l’authentification HTTP Basic

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("saad")
                .password("{noop}saad123") // mot de passe en clair (pour test uniquement)
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
