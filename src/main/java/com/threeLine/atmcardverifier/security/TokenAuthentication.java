package com.threeLine.atmcardverifier.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuthentication extends AbstractAuthenticationToken {

    private String accessToken;

    public TokenAuthentication(String accessToken, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.accessToken = accessToken;
        setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return accessToken;
    }
}