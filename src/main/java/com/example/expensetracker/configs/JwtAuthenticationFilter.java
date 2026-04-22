package com.example.expensetracker.configs;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.expensetracker.entities.User;
import com.example.expensetracker.repositories.UserRepository;
import com.example.expensetracker.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService service, UserRepository repository) {
        this.jwtService = service;
        this.userRepository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            final String email = jwtService.extractEmail(token);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null && email != null) {
                User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No such User found"));
                if (jwtService.isTokenValid(token, user)) {
                    UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(user, null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(newAuth);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}