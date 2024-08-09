package com.hana.amerikorea.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.
                authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers("/","/member/*","/api/asset/*","/api/portfolio/*").permitAll()
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .anyRequest().permitAll()
                );
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}