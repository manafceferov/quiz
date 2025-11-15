package com.jafarov.quiz.service;

import com.jafarov.quiz.config.auth.CustomParticipantDetails;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.repository.ParticipantRepository;
import com.jafarov.quiz.util.session.ParticipantSessionData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomParticipantDetailServiceTest {

    @Mock
    private ParticipantRepository repository;

    @Mock
    private ParticipantSessionData participantSessionData;

    @InjectMocks
    private CustomParticipantDetailService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_ParticipantExists() {
        Participant participant = new Participant();
        participant.setEmail("participant@test.com");

        when(repository.findByEmail("participant@test.com")).thenReturn(Optional.of(participant));

        UserDetails userDetails = service.loadUserByUsername("participant@test.com");

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof CustomParticipantDetails);
        verify(participantSessionData, times(1)).setParticipant(participant);
    }

    @Test
    void testLoadUserByUsername_ParticipantNotFound() {
        when(repository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                service.loadUserByUsername("unknown@test.com")
        );

        verify(participantSessionData, never()).setParticipant(any());
    }
}
