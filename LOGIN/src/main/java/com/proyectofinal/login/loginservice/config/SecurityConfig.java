package com.proyectofinal.login.loginservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ❌ Desactiva CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/auth/login").permitAll() // Permitir login
                        .requestMatchers(HttpMethod.POST, "/api/users/**").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll() // 👈 esto faltaba
                .anyRequest().authenticated()
)
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()) // ✅ forma correcta en Spring Security 6
                )
                .formLogin(form -> form.disable()) // importante deshabilitar login por formulario
                .httpBasic(basic -> basic.disable()); // y también deshabilitar http basic
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}