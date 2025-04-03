package org.example.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.entities.UserEntity;
import org.example.repository.IUserRoleRepository;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final IUserRoleRepository userRoleRepository;
    private static final String SECRET_KEY = "404E635266556A586E324042F413F4QWE8472B4BE9ZCX45367566B5970";

    public String generateAccessToken(UserEntity user) {
        var roles = userRoleRepository.findByUser(user);
        Date currentDate = new Date();
        Date expireDate = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);

        SecretKey key = getSecretKey();
        return Jwts.builder()
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("role", roles.stream()
                        .map(role -> role.getRole().getName())
                        .findFirst()
                        .orElse("USER"))
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key)
                .compact();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserId(String token) {
        return parseClaims(token).get("id", String.class);
    }

    public String getUsername(String token) {
        return parseClaims(token).get("username", String.class);
    }

    public Date getExpirationDate(String token) {
        return parseClaims(token).getExpiration();
    }

    public boolean validate(String token) {
        try {
            SecretKey key = getSecretKey();
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException ex) {
            System.out.println("\n\n\n\nInvalid JWT token - " + ex.getMessage());
        }
        return false;
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
