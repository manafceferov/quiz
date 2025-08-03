package com.jafarov.quiz.config.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.core.session.SessionRegistry;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomSessionAuthenticationStrategy implements SessionAuthenticationStrategy {
    private final String cookieName;
    private final RegisterSessionAuthenticationStrategy delegate;

    public CustomSessionAuthenticationStrategy(String cookieName, SessionRegistry sessionRegistry) {
        this.cookieName = cookieName;
        this.delegate = new RegisterSessionAuthenticationStrategy(sessionRegistry);
    }

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        response.addHeader("Set-Cookie", cookieName + "=" + session.getId() + "; Path=/; HttpOnly; SameSite=Lax");
        delegate.onAuthentication(authentication, request, response);
    }
}