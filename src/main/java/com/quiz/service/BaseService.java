package com.quiz.service;

import com.quiz.util.session.AuthSessionData;
import org.springframework.stereotype.Service;

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

