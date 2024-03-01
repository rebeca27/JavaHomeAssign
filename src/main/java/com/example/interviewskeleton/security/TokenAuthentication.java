package com.example.interviewskeleton.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class TokenAuthentication extends AbstractAuthenticationToken {

    private final String token;
    private final Object principal;

    public TokenAuthentication(String token, Object principal, boolean isAuthenticated) {
        super(null);
        this.token = token;
        this.principal = principal;
        setAuthenticated(isAuthenticated);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }
}
