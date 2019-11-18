package com.tamirou.springboot.jwt.JWTSpringSecurity.security.controller;

import com.tamirou.springboot.jwt.JWTSpringSecurity.exceptions.EntityNotFoundException;
import com.tamirou.springboot.jwt.JWTSpringSecurity.exceptions.NotValidException;
import com.tamirou.springboot.jwt.JWTSpringSecurity.models.*;
import com.tamirou.springboot.jwt.JWTSpringSecurity.repositories.*;
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
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public Iterable<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User postUser(@Valid @RequestBody User postedUser) {

        if (userRepository.existsByUsername(postedUser.getUsername()))
            throw new NotValidException("Le login " + postedUser.getUsername() + " existe déja");

        if (userRepository.existsByEmail(postedUser.getEmail()))
            throw new NotValidException("L'email " + postedUser.getEmail() + " existe déja");

        postedUser.setPassword(passwordEncoder.encode(postedUser.getPassword()));
        postedUser.setCreationDate(new Date());
        postedUser.setEnabled(true);
        postedUser.setIdUser(null);
        Optional<Role> role = roleRepository.findByName(RoleName.ROLE_USER);
        postedUser.getRoles().add(role.get());

        User savedUser = userRepository.save(postedUser);
        savedUser.setPassword(null);
        return savedUser;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/admin")
    public User postAdminUser(@Valid @RequestBody User postedUser) {

        if (userRepository.existsByUsername(postedUser.getUsername()))
            throw new NotValidException("Le login " + postedUser.getUsername() + " existe déja");

        if (userRepository.existsByEmail(postedUser.getEmail()))
            throw new NotValidException("L'email " + postedUser.getEmail() + " existe déja");

        postedUser.setPassword(passwordEncoder.encode(postedUser.getPassword()));
        postedUser.setCreationDate(new Date());
        postedUser.setEnabled(true);
        postedUser.setIdUser(null);
        Optional<Role> role = roleRepository.findByName(RoleName.ROLE_ADMIN);
        postedUser.getRoles().add(role.get());

        User savedUser = userRepository.save(postedUser);
        savedUser.setPassword(null);
        return savedUser;
    }

    @GetMapping("/users/profile")
    public User getUserInfos(Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        if (!user.isPresent())
            return null;

        return user.get();
    }

    @PostMapping("/users/changePassword")
        public boolean changePassword(@RequestBody User postedUser, Principal principal) {

        if(postedUser.getPassword().equals("") || postedUser.getPassword() == null)
            throw new NotValidException("Le mot de passe ne doit pas être null");

        if(postedUser.getNewPassword().equals("") || postedUser.getNewPassword() == null)
            throw new NotValidException("Le nouveau mot de passe ne doit pas être null");

        Optional<User> user = userRepository.findByUsername(principal.getName());

        Set<RoleName> roles = new HashSet<>();

        user.get().getRoles().forEach(role -> {
            roles.add(role.getName());
        });

        //authentification de l'utilisateur admin qui reinitialise un mot de passe ou un user qui change son mot de passe
        User userToModify = user.get();

        //si l'admin reinitialise le mot de passe d'un autre utilisateur : alors on ne modifie pas le mot de passe de l'utisateur connecté
        if (roles.contains(RoleName.ROLE_ADMIN) && postedUser.getIdUser() != userToModify.getIdUser()) {
            if(postedUser.getIdUser() == null || !userRepository.existsById(postedUser.getIdUser()))
                throw new NotValidException("Utilisateur à modifier invalide ou non trouvé");

            userToModify=userRepository.findById(postedUser.getIdUser()).get();
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userToModify.getUsername(),
                            postedUser.getPassword()
                    )
            );
        }catch (AuthenticationException e){
            throw new NotValidException("Mot de passe invalide");
        }

        //arrivé ici on aura vérifier le mot de passe fourni est correct que ca soit pour l'admin ou pour un user
        //on verifie que le nouveau mot de passe est different du mot de passe actuel pour un utilisateur qui change son mot de passe
        if (!roles.contains(RoleName.ROLE_ADMIN)) {
            if(postedUser.getPassword().equals(postedUser.getNewPassword()))
                throw new NotValidException("Le nouveau mot de passe doit être different du mot de passe actuel");
        }

        userToModify.setPassword(passwordEncoder.encode(postedUser.getNewPassword()));
        userRepository.save(userToModify);

        return true;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/{id}/changeStatus")
    public User changeStatus(@PathVariable Long id, @RequestParam Boolean status) {
        //fetching the user
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new EntityNotFoundException("User id-" + id);

        user.get().setEnabled(status);

        return userRepository.save(user.get());
    }
}


