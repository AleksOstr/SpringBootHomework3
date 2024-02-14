//package ru.geekbrains.springboothomework3.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableMethodSecurity(prePostEnabled = true)
//public class SecurityConfiguration {
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(configurer -> configurer
//                        .requestMatchers("/home").authenticated()
//                        .requestMatchers("/ui/books/**").authenticated()
//                        .requestMatchers("/ui/issues/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers("/ui/readers/**").hasAuthority("ROLE_READER")
//                )
//                .formLogin(Customizer.withDefaults())
//                .build();
//    }
//}
