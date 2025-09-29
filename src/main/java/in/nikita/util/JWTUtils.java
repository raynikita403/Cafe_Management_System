package in.nikita.util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils {

    // Use a fixed secret key (at least 32 chars for HS256)
    private static final String SECRET = "my-super-secret-key-which-is-at-least-32chars!";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

   // private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour....this is correct but while time of develop not good
    private final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 365; // for developing purpose


    public String generateToken(String username, String role) {
        System.out.println("Generating token for username: " + username + ", role: " + role);

        String token = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();

        System.out.println("Generated token: " + token);
        return token;
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
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
