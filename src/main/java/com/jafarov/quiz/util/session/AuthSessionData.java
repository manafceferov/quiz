package com.jafarov.quiz.util.session;

import org.springframework.stereotype.Component;

@Component
public class AuthSessionData {
    private final AdminSessionData adminSessionData;
    private final ParticipantSessionData participantSessionData;

    public AuthSessionData(AdminSessionData adminSessionData,
                           ParticipantSessionData participantSessionData
    ) {
        this.adminSessionData = adminSessionData;
        this.participantSessionData = participantSessionData;
    }

    public AdminSessionData getAdminSessionData() {
        return adminSessionData;
    }

    public ParticipantSessionData getParticipantSessionData() {
        return participantSessionData;
    }
}
