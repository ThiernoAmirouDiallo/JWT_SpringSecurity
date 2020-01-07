package com.tamirou.springboot.jwt.JWTSpringSecurity.security.controller;

import com.tamirou.springboot.jwt.JWTSpringSecurity.exceptions.EntityNotFoundException;
import com.tamirou.springboot.jwt.JWTSpringSecurity.exceptions.NotValidException;
import com.tamirou.springboot.jwt.JWTSpringSecurity.models.Role;
import com.tamirou.springboot.jwt.JWTSpringSecurity.models.RoleName;
import com.tamirou.springboot.jwt.JWTSpringSecurity.models.User;
import com.tamirou.springboot.jwt.JWTSpringSecurity.repositories.RoleRepository;
import com.tamirou.springboot.jwt.JWTSpringSecurity.repositories.UserRepository;
import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class HealthCheckController {

    @GetMapping("/health_check")
    public String healthCheck(){
        return "ok";
    }
}


