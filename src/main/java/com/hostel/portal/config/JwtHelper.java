package com.hostel.portal.config;

import com.hostel.portal.entity.token.JwtToken;
import com.hostel.portal.model.LoginModel;
import com.hostel.portal.repository.JwtTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    public String generateToken(LoginModel model) {
        Map<String,Object> claims=new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*1000))
                .subject(model.getEmail())
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        String secert="hjdghgfduyhtuyrutnbjvkndhkfhfgadjfhgggfhhgyhtuykjdjkhgjhfgjjngdhfguhgfsdgjhfvgjkhgjhfg";
//        System.out.println(Keys.hmacShaKeyFor(secert.getBytes()));
        return Keys.hmacShaKeyFor(secert.getBytes());
    }

    public String extractEmail(String actualToken) {
        return extractClaim(actualToken, Claims::getSubject);
    }

    private <T>T  extractClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build().parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String actualToken, UserDetails user) {
        final String userName=extractEmail(actualToken);
        return (userName.equals(user.getUsername()) && !isTokenExpired(actualToken) && checkExpiry(actualToken));
    }

    private boolean checkExpiry(String actualToken) {
        JwtToken jwt= jwtTokenRepository.findByToken(actualToken);
        return !jwt.getIsExpired();
    }

    private boolean isTokenExpired(String actualToken) {
        return extractExpiration(actualToken).before(new Date());
    }

    private Date extractExpiration(String actualToken) {
        return extractClaim(actualToken,Claims::getExpiration);
    }
}
