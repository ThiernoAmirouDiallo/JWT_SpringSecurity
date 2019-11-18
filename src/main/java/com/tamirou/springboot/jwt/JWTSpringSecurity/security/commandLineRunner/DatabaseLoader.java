package com.tamirou.springboot.jwt.JWTSpringSecurity.security.commandLineRunner;

import com.tamirou.springboot.jwt.JWTSpringSecurity.models.*;
import com.tamirou.springboot.jwt.JWTSpringSecurity.repositories.*;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseLoader(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        //roles
        Role role1 = new Role();
        role1.setName(RoleName.ROLE_ADMIN);
        Role role2 = new Role();
        role2.setName(RoleName.ROLE_USER);

        roleRepository.save(role1);
        roleRepository.save(role2);

        //admin
        User user = new User();
        user.setEmail("admin@platon.io");
        user.setEnabled(true);
        user.setUsername("admin");
        user.setFirstname("admin");
        user.setLastname("admin");
        user.setAdresse("admin");
        user.setPassword(encoder.encode("admin"));
        user.setCreationDate(new Date());
        Optional<Role> role = roleRepository.findByName(RoleName.ROLE_ADMIN);
        user.getRoles().add(role.get());
        this.userRepository.save(user);

        //user
        user = new User();
        user.setEmail("platon@platon.io");
        user.setEnabled(true);
        user.setUsername("platon");
        user.setFirstname("platon");
        user.setLastname("platon");
        user.setAdresse("platon");
        user.setPassword(encoder.encode("platon"));
        user.setCreationDate(new Date());
        role = roleRepository.findByName(RoleName.ROLE_USER);
        user.getRoles().add(role.get());
        this.userRepository.save(user);
    }
}
