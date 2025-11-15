package com.jafarov.quiz.config.auth;

import com.jafarov.quiz.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomAdminDetails implements UserDetails {

    private final Admin admin;
    private final Long id;
    private final String email;
    private final String password;

    public CustomAdminDetails(Admin admin) {
        this.admin = admin;
        this.id = admin.getId();
        this.email = admin.getEmail();
        this.password = admin.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return admin.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { return admin.getStatus(); }
}

