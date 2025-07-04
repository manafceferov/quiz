package com.jafarov.quiz.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login", "admin/**").permitAll() // login səhifəsi və resurslar azaddır
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/admin/login")                      // Sənin `login` səhifən
                        .loginProcessingUrl("/admin/login")             // `POST` sorğusu bu ünvana gəlməlidir
                        .defaultSuccessUrl("/admin/home", true)    // Daxil olandan sonra yönləndiriləcək
                        .failureUrl("/admin/login?error=true")          // Səhv daxil edilsə
                        .permitAll()
                );

        return http.build();
    }
}
