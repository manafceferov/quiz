package com.jafarov.quiz.service;

import com.jafarov.quiz.controller.CustomParticipantDetails;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.repository.ParticipantRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomParticipantDetailService implements UserDetailsService, AuthenticationSuccessHandler {
    private final ParticipantRepository repository;

    public CustomParticipantDetailService(ParticipantRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Participant participant = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Participant not found with email: " + email));

        return new CustomParticipantDetails(participant);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    }
}
