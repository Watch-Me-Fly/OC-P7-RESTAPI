package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Handles successful authentication events.
 * <p>
 *     Used to store the authenticated username in HTTP session, and redirects to bid list page
 * </p>
 *
 * @author Saja
 */
@Component
public class CustomSuccessAuthenticationHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomSuccessAuthenticationHandler.class);

    /**
     * Called when a user is authenticated successfully
     *
     * @param request the Http request
     * @param response the Http response
     * @param authentication the object containing user details
     * @throws IOException if an input or output exception occurs
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        // Store the username in the session
        session.setAttribute("username", username);

        User user = new User();
        user.setUsername(username);

        request.getSession().setAttribute("user", user);

        response.sendRedirect("/bidList/list");
    }
}
