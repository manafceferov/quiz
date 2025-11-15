package com.jafarov.quiz.service;

import com.jafarov.quiz.config.auth.CustomParticipantDetails;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.repository.ParticipantRepository;
import com.jafarov.quiz.util.session.ParticipantSessionData;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomParticipantDetailService implements UserDetailsService {

    private final ParticipantRepository repository;
    private final ParticipantSessionData participantSessionData;

    public CustomParticipantDetailService(ParticipantRepository repository,
                                          ParticipantSessionData participantSessionData
    ) {
        this.repository = repository;
        this.participantSessionData = participantSessionData;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Participant participant = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Participant not found with email: " + email));

        this.participantSessionData.setParticipant(participant);
        return new CustomParticipantDetails(participant);
    }
}
