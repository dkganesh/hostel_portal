package com.hostel.portal.config;

import com.hostel.portal.entity.token.JwtToken;
import com.hostel.portal.repository.JwtTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomLogoutHandler.class);
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token =request.getHeader("Authorization");
        String actualToken=null;
        if(token!=null && token.startsWith("Bearer ")) {
            actualToken = token.substring(7);
        }
        else try {
            throw new AuthenticationException("from logout service - custom logout handler - jwt token from header is empty");
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        try {
            boolean successfullyLoggedOut =exitCondition(actualToken);
            log.info("Successfully loggedout -> "+successfullyLoggedOut);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean exitCondition(String token) throws AuthenticationException {
        JwtToken jwt = jwtTokenRepository.findByToken(token);
        if(jwt!=null){
//        System.out.println("Before ->   "+jwt);
            log.info("in exit condition from custom logout handler jwt is not null...");
        jwt.setIsExpired(true);
        jwtTokenRepository.save(jwt);
//        System.out.println("After ->   "+jwt);
        return true;
        }
        else throw new
                AuthenticationException("from logout handler - " +
                "custom logout handler - jwt token not available in DB");
    }
}
