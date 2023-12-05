package com.toni.musify.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
/*
@Configuration
@EnableWebSecurity
public class BasicSecurityConfig {


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher("/swagger-resources/**", "/swagger-ui/**", "/swagger-ui.html/**", "/swagger-ui.html", "/v3/api-docs/**")
                .authorizeHttpRequests((auth)->auth.anyRequest().hasRole("USER"))
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A);
        UserDetails userDetails = User.builder().username("user")
                                                .password("password")
                                                .passwordEncoder(encoder::encode)
                                                .roles("USER")
                                                .build();
        manager.createUser(userDetails);
        return manager;
    }

}
*/