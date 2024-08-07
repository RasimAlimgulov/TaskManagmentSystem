package org.example.taskmanagmentsystem;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class TestTokenUtil {

    private static final String SECRET_KEY = "MIIBCgKCAQEAuFZtb3lhm0IlJg0vGgFEHXc/UohJwWeWdyuJgFqL2QpJH+enB"
            +"SEk7cD1LrkM+n7Z//n2Dkp/bztkUrHZfW4+GhFWvR0wXTBrg+ToBCWkxbo4fWZK"
            +"wJ7y8t2FYNEet9T1cQwA0eFsVf3FOaeixjM6zX4QlJCVd5xU9AbJbksdjAYYc2J"
            +"/ovV/gkBVGgakz2RZGbzVuGp38x0kCkCBgkC0CAwEAAQ==";

    public static String generateTestToken(String username) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}