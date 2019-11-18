package com.tamirou.springboot.jwt.JWTSpringSecurity.repositories;


import com.tamirou.springboot.jwt.JWTSpringSecurity.models.Role;
import com.tamirou.springboot.jwt.JWTSpringSecurity.models.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}