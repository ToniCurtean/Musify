package com.toni.musify.auth;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register/user")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.registerAdmin(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse>  authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
