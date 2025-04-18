package com.ada.pedidocompra.infraestrutura.configuracao.seguranca.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

public class JWTService {

    private static final String SECRET_KEY = "9A4nXhs3QjVEFCgHFZ+hT+icaBkLJJ4mcxhPhX/trbo"; // Deve ter pelo menos 256 bits
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private static final int EXPIRATION_TIME = 1000 * 60 * 10; // 10 minutos

    public static final String AUTH_HEADER = "Authorization";
    public static final String AUTH_BEARER = "Bearer ";

    public static String createToken(String email, List<String> roles) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(KEY).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static List<String> getRoles(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        if (claims == null || claims.get("roles") == null) {
            return List.of();
        }

        //noinspection unchecked
        return claims.get("roles", List.class);
    }

    public static String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (bearerToken != null && bearerToken.startsWith(AUTH_BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static String getUserContext() {
        var userAuthToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();

        return userAuthToken.getPrincipal().toString();
    }

}
