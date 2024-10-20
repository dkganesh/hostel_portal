package com.hostel.portal.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtHelper helper;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        System.out.println(4);
        log.info("Request header : "+request.getHeader("Authorization"));
        String token =request.getHeader("Authorization");
        String actualToken=null;
        String email=null;
        if(token!=null && token.startsWith("Bearer ")) {
            actualToken = token.substring(7);
            email=helper.extractEmail(actualToken);
        }
//        System.out.println("in doFilter JWTFILTER -> -> "+email+"->   "+actualToken);
        log.info("in JWTFilter -> "+email +"\n actual token -> "+actualToken);

        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails user = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(email);
            if(helper.validateToken(actualToken,user)){
//                System.out.println("inside inside");
//                System.out.println(user);
                log.info("jwt token successfully validated by time,db and email");
                UsernamePasswordAuthenticationToken userToken=
                        new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
//                System.out.println(userToken);
                SecurityContextHolder.getContext().setAuthentication(userToken);
                log.info("jwt token authentication succesfull");
            }
        }

        filterChain.doFilter(request,response);
    }
}
