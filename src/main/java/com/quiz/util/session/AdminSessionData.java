package com.quiz.util.session;

import com.quiz.entity.Admin;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdminSessionData {

    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Long getId() {
        return admin.getId();
    }

    public String getFirstName() {
        return admin.getFirstName();
    }

    public String getLastName() {
        return admin.getLastName();
    }

    public String getFatherName() {
        return admin.getFatherName();
    }

    public String getFullName() {
        return (admin.getFirstName() + " " + admin.getLastName() + " " + admin.getFatherName());
    }

    public String getEmail() {
        return admin.getEmail();
    }

}
