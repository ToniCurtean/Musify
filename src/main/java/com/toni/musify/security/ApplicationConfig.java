package com.toni.musify.security;

import com.toni.musify.domain.user.model.UserCredentials;
import com.toni.musify.domain.user.model.UserRole;
import com.toni.musify.domain.user.repository.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.util.Objects.nonNull;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @SneakyThrows
    @Nullable
    public static Integer getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(nonNull(authentication) && authentication.getPrincipal() instanceof UserCredentials principal){
            return principal.id();
        }
        return null;
    }

    @SneakyThrows
    @Nullable
    public static String getCurrentUserRole(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(nonNull(authentication) && authentication.getPrincipal() instanceof UserCredentials principal){
            return principal.role().toString();
        }
        return null;
    }
}
