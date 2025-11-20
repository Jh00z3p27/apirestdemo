package edu.sv.ues.dam235.apirestdemo.utilities;

import edu.sv.ues.dam235.apirestdemo.dtos.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtil {


    private final String secretKey = "CPqiMDqBnNEXawGoFX7g";

    public String extractUsername(String token) {
        if (token == null) return null;
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        if (token == null) return null;
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        if (claims == null) return null;
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            // Token inválido o parse error -> retornamos null (manejar en el caller)
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        Date exp = extractExpiration(token);
        if (exp == null) return true;
        return exp.before(new Date());
    }

    public TokenDTO generateToken(String userName, UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(); // evitar raw types
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put("authorities", authorities);
        return createToken(claims, userName);
    }

    private TokenDTO createToken(Map<String, Object> claims, String userName) {
        TokenDTO newToken = new TokenDTO();
        // expireIn como string (milisegundos). Si prefieres long, cambia TokenDTO y este valor.
        newToken.setExpireIn("1800000");

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1_800_000L)) // 30 minutos
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        newToken.setToken(token);
        return newToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        if (token == null || userDetails == null) return false;
        final String userName = extractUsername(token);
        return (userName != null && userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean validatedTokenPermission(String token) {
        try {
            if (token == null) return false;
            String email = extractUsername(token);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
