package com.jafarov.quiz.config.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class CustomSessionCookieFilter extends OncePerRequestFilter {
    private final String cookieName;

    public CustomSessionCookieFilter(String cookieName) {
        this.cookieName = cookieName;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            response.addHeader("Set-Cookie", cookieName + "=" + session.getId() + "; Path=/; HttpOnly; SameSite=Lax");
        }
        filterChain.doFilter(request, response);
    }
}