package com.quiz.util.session;

import com.quiz.entity.Participant;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component()
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ParticipantSessionData {

    private Participant participant;

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant admin) {
        this.participant = admin;
    }

    public Long getId() {
        return participant.getId();
    }

    public String getFirstName() {
        return participant.getFirstName();
    }

    public String getLastName() {
        return participant.getLastName();
    }

    public String getFatherName() {
        return participant.getFatherName();
    }

    public String getFullName() {
        return (participant.getFirstName() + " " + participant.getLastName() + " " + participant.getFatherName());
    }

    public String getEmail() {
        return participant.getEmail();
    }

}
