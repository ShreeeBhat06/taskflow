package com.taskflow.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers("/auth/**").permitAll()
            	    .requestMatchers(HttpMethod.GET, "/tasks/**").hasAnyRole("USER", "ADMIN")
            	    .requestMatchers(HttpMethod.POST, "/tasks/**").hasRole("ADMIN")
            	    .requestMatchers(HttpMethod.PUT, "/tasks/**").hasRole("ADMIN")
            	    .requestMatchers(HttpMethod.DELETE, "/tasks/**").hasRole("ADMIN")
            	    .anyRequest().authenticated()
            	) 
            .exceptionHandling(ex -> ex
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType("application/json");
                        response.getWriter().write(
                            "{\"success\":false,\"message\":\"Access denied: you don't have permission\",\"data\":null}"
                        );
                    })
                )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  

            return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}