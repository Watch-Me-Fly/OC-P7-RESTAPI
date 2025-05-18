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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final CustomFailureAuthenticationHandler failHandler = new CustomFailureAuthenticationHandler();
    private final CustomSuccessAuthenticationHandler successHandler = new CustomSuccessAuthenticationHandler();

    // Manage authorizations
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

    // encrypt passwords
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        logger.info("Password Encoder");
        return new BCryptPasswordEncoder();
    }

    // manage authentifications
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