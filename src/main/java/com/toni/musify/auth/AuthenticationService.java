package com.toni.musify.auth;

import com.toni.musify.domain.user.mappers.UserMapper;
import com.toni.musify.domain.user.model.User;
import com.toni.musify.domain.user.model.UserDTO;
import com.toni.musify.domain.user.model.UserRole;
import com.toni.musify.domain.user.model.UserViewDTO;
import com.toni.musify.domain.user.repository.UserRepository;
import com.toni.musify.security.jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    //private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthenticationResponse registerUser(RegisterRequest request) {
        UserDTO toAdd = new UserDTO(0, request.getFirstName(), request.getLastName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getCountry(), UserRole.REGULAR,null);
        User saved = userRepository.save(userMapper.toEntity(toAdd));
        saved.setUserId(saved.getId());
        userRepository.save(saved);
        var jwtToken = jwtService.generateToken(userMapper.toViewDTO(saved));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        UserDTO toAdd = new UserDTO(0, request.getFirstName(), request.getLastName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getCountry(), UserRole.ADMIN,null);
        User saved = userRepository.save(userMapper.toEntity(toAdd));
        saved.setUserId(saved.getId());
        userRepository.save(saved);
        var jwtToken = jwtService.generateToken(userMapper.toViewDTO(saved));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
                throw new AuthenticationException("Password is incorrect");
            var jwtToken = jwtService.generateToken(userMapper.toViewDTO(user));
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }catch (Exception ex){
            log.error(ex.getMessage());
            return null;
        }

    }
}
