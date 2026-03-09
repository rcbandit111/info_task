//package com.sunlex.be.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
//public class DefaultSecurityConfig {
//
//    @Bean
//    @Order(1)
//    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
//
//        http.cors(cors -> cors.configurationSource(request -> {
//            var corsConfig = new CorsConfiguration();
//            corsConfig.setAllowedOrigins(List.of("*"));
//            corsConfig.setAllowedMethods(List.of("*"));
//            corsConfig.setAllowedHeaders(List.of("*"));
//            return corsConfig;
//        }));
//
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth ->
//                        auth.anyRequest().permitAll()
//                )
//                .build();
//    }
//}
