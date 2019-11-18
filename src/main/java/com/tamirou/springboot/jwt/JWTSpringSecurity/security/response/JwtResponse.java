package com.tamirou.springboot.jwt.JWTSpringSecurity.security.response;

import com.tamirou.springboot.jwt.JWTSpringSecurity.models.RoleName;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    Date issuedAt;
    Date expiresAt;
    private Set<RoleName> roles = new HashSet<>();


    public JwtResponse(String accessToken, Date issuedAt, Date expiresAt) {
        this.token = accessToken;
        this.issuedAt=issuedAt;
        this.expiresAt=expiresAt;
    }


}