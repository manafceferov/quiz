package com.jafarov.quiz.service;

import com.jafarov.quiz.config.auth.CustomAdminDetails;
import com.jafarov.quiz.entity.Admin;
import com.jafarov.quiz.repository.AdminRepository;
import com.jafarov.quiz.util.session.AdminSessionData;
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

class CustomAdminDetailsServiceTest {

    @Mock
    private AdminRepository repository;

    @Mock
    private AdminSessionData adminSessionData;

    @InjectMocks
    private CustomAdminDetailsService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        Admin admin = new Admin();
        admin.setEmail("admin@test.com");

        when(repository.findByEmail("admin@test.com")).thenReturn(Optional.of(admin));

        UserDetails userDetails = service.loadUserByUsername("admin@test.com");

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof CustomAdminDetails);
        verify(adminSessionData, times(1)).setAdmin(admin);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(repository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                service.loadUserByUsername("unknown@test.com")
        );

        verify(adminSessionData, never()).setAdmin(any());
    }
}
