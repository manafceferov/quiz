package com.jafarov.quiz.service;

import com.jafarov.quiz.util.session.AuthSessionData;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
public class BaseService {

    private final AuthSessionData authSessionData;

    public BaseService(AuthSessionData authSessionData) {
        this.authSessionData = authSessionData;
    }

    public AuthSessionData getAuthSessionData() {
        return authSessionData;
    }
}

