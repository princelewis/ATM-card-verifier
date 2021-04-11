package com.threeLine.atmcardverifier.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    TokenVerifier tokenVerifier;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
                String token = getTokenFromHeader(httpServletRequest);
                 String appKey = httpServletRequest.getHeader("appKey");
                String timeStamp = httpServletRequest.getHeader("timeStamp");

                boolean isTokenAuthentic = tokenVerifier.verifyToken(token, appKey, timeStamp);

                boolean hasOtherHeaderProperties = checkOtherHeaderProperties(httpServletRequest);

                if (StringUtils.hasText(token) && isTokenAuthentic && hasOtherHeaderProperties) {


                    TokenAuthentication authentication = new TokenAuthentication(token, AuthorityUtils.NO_AUTHORITIES);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                }

        } catch(Exception ex) {
            log.error("An error occurred while trying to authenticate token with error message -- {}", ex.getMessage(), ex);
        }

        //Pass on the request and response to the next filter to do it's job
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    //Fetch token from the header to verify if token is correct
    private String getTokenFromHeader(HttpServletRequest request) {
        String threeLineToken = request.getHeader("Authorization");
        if(StringUtils.hasText(threeLineToken) && threeLineToken.startsWith("3line ")) {
            return threeLineToken.substring(6);
        }

        return null;
    }

    //Check if the header has other properties
    private boolean checkOtherHeaderProperties(HttpServletRequest request) {
        String appKey = request.getHeader("appKey");
        String timeStamp = request.getHeader("timeStamp");

        if (StringUtils.hasText(appKey) && StringUtils.hasText(timeStamp)) return true;

        return false;
    }
}
