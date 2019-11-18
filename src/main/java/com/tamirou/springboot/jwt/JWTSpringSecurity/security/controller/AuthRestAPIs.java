package com.tamirou.springboot.jwt.JWTSpringSecurity.security.controller;

import com.tamirou.springboot.jwt.JWTSpringSecurity.models.User;
import com.tamirou.springboot.jwt.JWTSpringSecurity.repositories.RoleRepository;
import com.tamirou.springboot.jwt.JWTSpringSecurity.repositories.UserRepository;
import com.tamirou.springboot.jwt.JWTSpringSecurity.security.jwt.JwtProvider;
import com.tamirou.springboot.jwt.JWTSpringSecurity.security.request.LoginForm;
import com.tamirou.springboot.jwt.JWTSpringSecurity.security.response.JwtResponse;
import java.security.Principal;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        System.out.println("0");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        System.out.println("1");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("2");

        JwtResponse jwt = jwtProvider.generateJwtToken(authentication);
        System.out.println("3");

        Optional<User> user= userRepository.findByUsername(loginRequest.getUsername());
        System.out.println("4");

        //jwt.setRoles(user.get().getRoles());
        user.get().getRoles().forEach(role -> {
            jwt.getRoles().add(role.getName());
        });
        System.out.println("5");

        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> authenticateUser(Principal principal) {
        Optional<User> user= userRepository.findByUsername(principal.getName());

        JwtResponse jwt = jwtProvider.generateJwtToken(principal.getName());

        //jwt.setRoles(user.get().getRoles());
        user.get().getRoles().forEach(role -> {
            jwt.getRoles().add(role.getName());
        });
        return ResponseEntity.ok(jwt);
    }

}
