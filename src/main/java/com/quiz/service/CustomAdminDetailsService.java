package com.quiz.service;

import com.quiz.config.auth.CustomAdminDetails;
import com.quiz.entity.Admin;
import com.quiz.repository.AdminRepository;
import com.quiz.util.session.AdminSessionData;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAdminDetailsService implements UserDetailsService {

    private final AdminRepository repository;
    private final AdminSessionData adminSessionData;

    public CustomAdminDetailsService(AdminRepository repository,
                                     AdminSessionData adminSessionData
    ) {
        this.repository = repository;
        this.adminSessionData = adminSessionData;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        this.adminSessionData.setAdmin(admin);
        return new CustomAdminDetails(admin);
    }
}
