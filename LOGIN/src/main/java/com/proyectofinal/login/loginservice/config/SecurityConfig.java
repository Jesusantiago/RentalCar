package com.proyectofinal.login.loginservice.config;

import com.proyectofinal.login.loginservice.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ❌ Desactiva CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console", "/h2-console/" , "/h2-console/**").permitAll()
                        .requestMatchers("/api/auth/login", "/api/users/register").permitAll() // públicas
                        .requestMatchers("/api/users/**").authenticated() // cualquiera con token puede acceder
                        .anyRequest().authenticated() // cualquier otra ruta también necesita estar autenticado
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // 👈🏻 Agregar esta línea
                .formLogin(form -> form.disable()) // importante deshabilitar login por formulario
                .httpBasic(basic -> basic.disable()); // y también deshabilitar http basic
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

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
