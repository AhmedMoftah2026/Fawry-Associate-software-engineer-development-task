package com.ahmed.move.jwt;

import com.ahmed.move.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtTokenProvider {

    private String jwtSecret = "MOVIEAPPMOVIEAPPMOVIEAPPMOVIEAPPMOVIEAPPMOVIEAPPMOVIEAPPMOVIEAPPMOVIEAPPMOVIEAPPMOVIEAPPMOVIEAPP";
    private long jwtExpiration = 300000000;


    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    private String getClaimFromToken(String token, String key) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get(key, String.class);
    }


    public String getRoleFromToken(String token) {
        return getClaimFromToken(token, "role");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}


