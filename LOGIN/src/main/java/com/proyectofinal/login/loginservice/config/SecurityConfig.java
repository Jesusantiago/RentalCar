package com.proyectofinal.login.loginservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ❌ Desactiva CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll() // 👈 esto faltaba
                .anyRequest().authenticated()
)
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()) // ✅ forma correcta en Spring Security 6
                );

        return http.build();
    }
}













//package com.proyectofinal.login.loginservice.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()
////                        .ignoringRequestMatchers("/h2-console/**")
//                )
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .headers(headers -> headers
//                        .frameOptions().disable()
//                );
//
//        return http.build();
//    }
//}
