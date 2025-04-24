package com.examly.springapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain createChain(HttpSecurity http) throws Exception {
        http.cors(cors->cors.disable())
        .csrf(csrf->csrf.disable())
        .authorizeHttpRequests(auth->auth
        .anyRequest().permitAll());
        return http.build();
    }
} 