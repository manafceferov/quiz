package com.jafarov.quiz.common;

import com.jafarov.quiz.admin.dto.user.UserInsertRequest;
import com.jafarov.quiz.admin.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    public final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserService userService,
                           PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        initUsers();
    }

    public void initUsers() {
        List<UserInsertRequest> userRequest = new LinkedList<UserInsertRequest>();

        userRequest.add(
                new UserInsertRequest(1L,
                        "Anar",
                        "Cəfərov",
                        "İlham",
                        "anarceferov1996@gmail.com",
                        "123456"
                )
        );

        userRequest.add(
                new UserInsertRequest(2L,
                        "Manaf",
                        "Cəfərov",
                        "İlham",
                        "mjafarov21@gmail.com",
                        "123456"
                )
        );

        userService.saveAll(userRequest);
    }
}
