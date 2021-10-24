package io.chat.java.api.config.security.jwt;

import io.chat.java.api.config.security.jwt.JwtPropertiesLoader;
import io.chat.java.api.domain.user.model.AuthenticationUserDetails;
import io.chat.java.api.util.JsonHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final JwtPropertiesLoader jwtPropertiesLoader;
    @Autowired
    public JwtUtil(JwtPropertiesLoader jwtPropertiesLoader) {
        this.jwtPropertiesLoader = jwtPropertiesLoader;
    }

    public String extractLoginId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T>T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtPropertiesLoader.getSecret())
                .parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String createJwtToken(AuthenticationUserDetails details) {
        if (StringUtils.isBlank(details.getLoginId())) {
            throw new IllegalArgumentException("Cannot create JWT Token without LoginId");
        }

        Claims claims = Jwts.claims().setSubject(details.getLoginId());
        claims.put("details", JsonHelper.toJson(details));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(jwtPropertiesLoader.getExpiration())))
                .signWith(SignatureAlgorithm.HS256, jwtPropertiesLoader.getSecret())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String loginId = extractLoginId(token);
        return (loginId.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

}
