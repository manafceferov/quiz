package com.jafarov.quiz.service;

import com.jafarov.quiz.config.auth.CustomParticipantDetails;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.repository.ParticipantRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomParticipantDetailService implements UserDetailsService {
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
}
