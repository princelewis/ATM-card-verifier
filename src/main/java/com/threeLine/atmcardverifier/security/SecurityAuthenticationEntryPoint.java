package com.threeLine.atmcardverifier.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        log.info("Authenticating user failed  with error message ****** {}", e.getMessage());
        /*In case a user is trying to access an unauthorized URL, display
        this error message
         */

        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());

    }
}
