package com.springbreakers.geektext.config;

import com.springbreakers.geektext.model.User;
import com.springbreakers.geektext.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Configuration
@EnableMethodSecurity
public class SecurityConfig extends OncePerRequestFilter {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex.accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.getWriter().write("""
                {
                  "status": 403,
                  "error": "Forbidden",
                  "message": "You are not authorized to perform this action."
                }
            """);
                }))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .addFilterBefore(this, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader("Authorization");
        if (apiKey != null && !apiKey.isEmpty()) {
            Optional<User> optionalUser = userService.getUsers().stream()
                    .filter(u -> apiKey.equals(u.getSessionApiKey()))
                    .findFirst();
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(authority));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
