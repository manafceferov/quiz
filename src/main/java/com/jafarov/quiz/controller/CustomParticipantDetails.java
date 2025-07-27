package com.jafarov.quiz.controller;

import com.jafarov.quiz.entity.Participant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class CustomParticipantDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;

    public CustomParticipantDetails(Participant participant) {
        this.id = participant.getId();
        this.email = participant.getEmail();
        this.password = participant.getPassword();
    }

    public Long getId() { return id; }

    @Override public String getUsername() { return email; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
