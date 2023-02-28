package by.dudko.questionnaires.util;

import by.dudko.questionnaires.dto.user.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtils {
    private final int liveTime;
    private final Key signingKey;
    private final String issuer;

    public JwtUtils(@Value("${app.jwt.live-time}") int liveTime,
                    @Value("${app.jwt.secret}") String secretKey,
                    @Value("${app.jwt.issuer}") String issuer) {
        this.liveTime = liveTime;
        this.signingKey = buildSigningKey(secretKey);
        this.issuer = issuer;
    }

    public String generateToken(UserDetailsImpl userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetailsImpl userDetails) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + liveTime);
        return Jwts.builder()
                .addClaims(claims)
                .claim("id", userDetails.getId())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .setIssuer(issuer)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .requireIssuer(issuer)
                .build();
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.warn("Invalid token: " + token, e);
            return false;
        }
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .requireIssuer(issuer)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    private Key buildSigningKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
