package com.dalgo.SpringBoot.config;

import com.dalgo.SpringBoot.Service.UserService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration // @Configure evabeo likhte partam. tahole oi file ta import kora lagto. r ekhane import na korei direct  lekh ahoyeche
@EnableWebSecurity
public class SecurityConfig {

    @Bean   // @Bean na dile auto security disable hoyna. Login form open hoy. bean dile r open hoyna
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/health/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());


    return http.build();
    }


    // manually cod kore username password roll etc set kora. without database
//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user1 = User
//                .withUsername("Howlader")
//                .password("{noop}13579")
//                .roles("USER")
//                .build();
//        UserDetails user2 = User
//                .withUsername("Priya")
//                .password("{noop}13579")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2);
//    }


    // Database theke username and password niye auth provide
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserService();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authenticationProvider;

    }


}
