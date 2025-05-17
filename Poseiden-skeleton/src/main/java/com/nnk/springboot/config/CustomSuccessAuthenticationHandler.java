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

@Component
public class CustomSuccessAuthenticationHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomSuccessAuthenticationHandler.class);

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
