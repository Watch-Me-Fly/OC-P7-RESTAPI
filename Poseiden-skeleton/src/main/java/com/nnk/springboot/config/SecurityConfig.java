package com.nnk.springboot.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Spring Security.
 *
 * <ul>
 *     <li>Set up HTTP security rules</li>
 *     <li>password encoding</li>
 *     <li>authentication management</li>
 *     <li>login and logout handling using Spring Security</li>
 * </ul>
 *
 * @author Saja
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final CustomFailureAuthenticationHandler failHandler = new CustomFailureAuthenticationHandler();
    private final CustomSuccessAuthenticationHandler successHandler = new CustomSuccessAuthenticationHandler();

    /**
     * Define security filter chain for Http requests
     *
     * <p>
     * Configures public access to ressources, pages, and operations.
     * All other endpoints require authentication.
     * </p>
     *
     * @param http the {@link HttpSecurity} to modify
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception in case of configuration error
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Security Filter Chain");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> { auth
                        // Public access ------------------------------------------
                        // static ressources
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // authentication
                        .requestMatchers("/login", "/app/login").permitAll()
                        // user
                        .requestMatchers("/user/add", "/user/validate", "/user/list").permitAll()
                        .requestMatchers("/user/update/**", "/user/delete/**").permitAll()
                        // other pages
                        .requestMatchers("/").permitAll()
                        // limit access if not connected
                        .anyRequest().authenticated();
                })

                // login
                .formLogin(form -> form
                        .loginPage("/app/login")
                        .loginProcessingUrl("/app/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                        // for a more user-friendly feedback behaviour
                        .failureHandler(failHandler)
                        .successHandler(successHandler)
                )
                // logout
                .logout(logout -> logout
                        .logoutUrl("/app-logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    /**
     * Provides a {@link BCryptPasswordEncoder} bean for password hashing.
     *
     * @return a new instance of {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        logger.info("Password Encoder");
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an {@link AuthenticationManager} bean using a {@link DaoAuthenticationProvider}
     *
     * @param userDetailsService the service used to load user data
     * @param bCryptPasswordEncoder the encoder used to hash and verify passwords
     * @return the configured {@link AuthenticationManager}
     * @throws Exception if an errors occurs while building
     */
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return new ProviderManager(authProvider);
    }

}