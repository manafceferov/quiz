package com.jafarov.quiz.config.auth;

import com.jafarov.quiz.service.CustomParticipantDetailService;
import com.jafarov.quiz.service.CustomAdminDetailsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomAdminDetailsService adminDetailsService;
    private final CustomParticipantDetailService participantDetailsService;

    public SecurityConfig(
            CustomAdminDetailsService adminDetailsService,
            CustomParticipantDetailService participantDetailsService
    ) {
        this.adminDetailsService = adminDetailsService;
        this.participantDetailsService = participantDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityContextRepository adminSecurityContextRepository() {
        HttpSessionSecurityContextRepository repository = new HttpSessionSecurityContextRepository();
        repository.setSpringSecurityContextKey("ADMIN_SECURITY_CONTEXT");
        return repository;
    }

    @Bean
    public SecurityContextRepository participantSecurityContextRepository() {
        HttpSessionSecurityContextRepository repository = new HttpSessionSecurityContextRepository();
        repository.setSpringSecurityContextKey("PARTICIPANT_SECURITY_CONTEXT");
        return repository;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SessionAuthenticationStrategy adminSessionAuthenticationStrategy(SessionRegistry sessionRegistry) {
        return new CustomSessionAuthenticationStrategy("ADMIN_JSESSIONID", sessionRegistry);
    }

    @Bean
    public SessionAuthenticationStrategy participantSessionAuthenticationStrategy(SessionRegistry sessionRegistry) {
        return new CustomSessionAuthenticationStrategy("PARTICIPANT_JSESSIONID", sessionRegistry);
    }

    @Bean
    public LogoutHandler customLogoutHandler() {
        return (request, response, authentication) -> {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String contextKey = request.getRequestURI().startsWith("/admin") ? "ADMIN_SECURITY_CONTEXT" : "PARTICIPANT_SECURITY_CONTEXT";
                session.removeAttribute(contextKey);
            }
        };
    }

    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http,
                                                        SessionAuthenticationStrategy adminSessionAuthenticationStrategy,
                                                        LogoutHandler customLogoutHandler
    ) throws Exception {
        http
                .securityMatcher("/admin/**")
                .securityContext(context -> context
                        .securityContextRepository(adminSecurityContextRepository())
                )
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession()
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionAuthenticationStrategy(adminSessionAuthenticationStrategy)
                        .maximumSessions(1)
                        .sessionRegistry(sessionRegistry())
                        .expiredUrl("/admin/login?expired")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login", "/admin/sb-admin/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest()
                        .denyAll()
                )
                .userDetailsService(adminDetailsService)
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/home", true)
                        .failureUrl("/admin/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login?logout=true")
                        .addLogoutHandler(customLogoutHandler)
                        .invalidateHttpSession(false) // Sessiyanı tam ləğv etmə
                        .clearAuthentication(true)
                        .permitAll()
                )
                .addFilterAfter(new CustomSessionCookieFilter("ADMIN_JSESSIONID"), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain publicSecurityFilterChain(HttpSecurity http,
                                                         SessionAuthenticationStrategy participantSessionAuthenticationStrategy,
                                                         LogoutHandler customLogoutHandler
    ) throws Exception {
        http
                .securityContext(context -> context
                        .securityContextRepository(participantSecurityContextRepository())
                )
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession()
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionAuthenticationStrategy(participantSessionAuthenticationStrategy)
                        .maximumSessions(1)
                        .sessionRegistry(sessionRegistry())
                        .expiredUrl("/login?expired")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/logout").permitAll()
                        .anyRequest()
                        .permitAll()
                )
                .userDetailsService(participantDetailsService)
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/")
                        .addLogoutHandler(customLogoutHandler)
                        .invalidateHttpSession(false)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .addFilterAfter(new CustomSessionCookieFilter("PARTICIPANT_JSESSIONID"), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}