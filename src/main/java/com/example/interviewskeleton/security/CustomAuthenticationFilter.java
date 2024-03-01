package com.example.interviewskeleton.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_TOKEN_HEADER = "X-Auth-Token";
    private static final String AUTH_TOKEN_VALUE = "such-secure-much-wow";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authToken = request.getHeader(AUTH_TOKEN_HEADER);

        if (AUTH_TOKEN_VALUE.equals(authToken)) {
            Authentication auth = new TokenAuthentication(authToken, null, true);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}

