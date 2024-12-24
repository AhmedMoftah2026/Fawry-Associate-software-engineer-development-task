package com.ahmed.move.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authrequest ->{
//            authrequest.requestMatchers("/auth/login").permitAll()
            authrequest.requestMatchers("/movie/search-in-OMDB","/movie/delete/**","/movie/search-in-OMDB-And-AddtoDB/**","/movie//addoutsideOMDB").hasRole("ADMIN");
//            authrequest.requestMatchers("/movie/add", "/movie/delete/**").hasRole("ADMIN")
            authrequest.anyRequest().authenticated();
        });
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
//        http.logout(Customizer.withDefaults());


        return http.build();
    }}

