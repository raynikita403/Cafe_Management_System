package in.nikita.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils {

    private static final String SECRET = "my-super-secret-key-which-is-at-least-32chars!";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 1 year for development (change to 1 hr in prod)
    private final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 365;

    // ✅ removed the unused 3rd param
    public String generateToken(String username, String role, String fullName) {
        return Jwts.builder()
                .setSubject(username) // subject = username (unique login ID / email)
                .claim("role", role)  // role claim
                .claim("name", fullName) // store full name for Welcome message
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public String extractName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("name", String.class);
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
