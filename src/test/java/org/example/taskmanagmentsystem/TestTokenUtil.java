package org.example.taskmanagmentsystem;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class TestTokenUtil {

    private static String SECRET_KEY="EPy_mM65kbYCRHDVtsMkxReKnkPJouUnxFl2R2Cx2d-qKgIg-bdNa-Pv_X5jMdOb1T7hq05Xb3Iz_DmdnMowTg==";
    private static Key key=Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    public static String generateTestToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}