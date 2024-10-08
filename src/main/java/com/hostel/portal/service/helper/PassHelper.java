package com.hostel.portal.service.helper;

import com.hostel.portal.model.PassModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class PassHelper {
    public String generatePassToken(PassModel model) throws ParseException {
//        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = dateFormat.parse(model.getStartDate());
        Date endDate = dateFormat.parse(model.getEndDate());

        Map<String,Object> claims=new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .issuedAt(startDate)
                .expiration(endDate)
                .subject(model.getEmail())
                .and()
                .signWith(getKey())
                .compact();
    }
    private SecretKey getKey() {
        String secert="hjdghgfduyhtuyrutnbjvkndhkfhfgadjfhgggfhhgyhtuykjdjkhgjhfgjjngdhfguhgfsdgjhfvgjkhgjhfg";
        return Keys.hmacShaKeyFor(secert.getBytes());
    }

    public boolean validatePassToken(String actualToken) {
        return !isTokenExpired(actualToken);
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
    private boolean isTokenExpired(String actualToken) {
//        System.out.println(extractExpiration(actualToken));
        Date exp=extractExpiration(actualToken);
        return exp.before(new Date()) || exp.equals(new Date());
    }

    private Date extractExpiration(String actualToken) {
        return extractClaim(actualToken, Claims::getExpiration);
    }
}
