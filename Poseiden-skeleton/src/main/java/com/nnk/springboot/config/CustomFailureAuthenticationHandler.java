package com.nnk.springboot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles failed authentication events.
 * <ul>
 *     <li>Logs the failed attempt</li>
 *     <li>Sets a 401 Http status</li>
 *     <li>Redirects the user to login page</li>
 *     <li>Returns a JSON error response</li>
 * </ul>
 *
 * @author Saja
 */
@Component
public class CustomFailureAuthenticationHandler implements AuthenticationFailureHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomFailureAuthenticationHandler.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Called when a user's authentication fails
     *
     * @param request the Http request
     * @param response the Http response
     * @param exception the exception which was thrown to indicate the error
     * @throws IOException if an input or an output exception occurs
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        log.info("onAuthenticationFailure");
        log.error("Login failed: {}", exception.getMessage());

        // http status 401
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.sendRedirect("/app/login?error=true");

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Nom d'utilisateur ou mot de passe invalid");

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
