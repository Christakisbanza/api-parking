package com.api_park.demo_api_parking.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsServices userDetailsServices;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION);

        if(token == null || !token.startsWith(JwtUtils.JWT_BEARER)){
            log.info("JWT Token está null ou vazio ou não iniciado com Bearer");
            filterChain.doFilter(request,response);
            return;
        }
        if (!JwtUtils.isTokenValid(token)){
            log.warn("JWT Token está inválido");
            filterChain.doFilter(request,response);
            return;
        }

        String userName = JwtUtils.getUserNameFromToken(token);

        toAuthentication(request, userName);

        filterChain.doFilter(request, response);
    }

    private void toAuthentication(HttpServletRequest request, String userName) {
        UserDetails userDetails = userDetailsServices.loadUserByUsername(userName);
        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null,userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
