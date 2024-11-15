package com.example.springaop_hw2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(source ->
                {
                    Map<String, Object> authorities = source.getClaim("realm_access");
                    List<String> roles = (List<String>) authorities.get("roles");

                    return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

                });

        http
                .csrf(d -> d.disable())
                .authorizeRequests(source -> source
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(config ->
                        config.jwt(jwtConfig ->
                                    jwtConfig.jwtAuthenticationConverter(jwtAuthenticationConverter)
                                )
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}