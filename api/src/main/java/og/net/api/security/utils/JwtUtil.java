package og.net.api.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    public String gerarToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256("senha123");
        return JWT.create().withIssuer("WEG")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 300000))
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
    }

    public String getEmail(String token) {
        return JWT.decode(token).getSubject();
    }
}
