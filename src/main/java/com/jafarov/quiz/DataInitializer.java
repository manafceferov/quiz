package com.jafarov.quiz;

import com.jafarov.quiz.dto.admin.AdminInsertRequest;
import com.jafarov.quiz.service.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    public final AdminService adminService;

    public DataInitializer(AdminService adminService
    ) {
        this.adminService = adminService;
    }

    @Override
    public void run(String... args) throws Exception {
        initUsers();
    }

    public void initUsers() {
        List<AdminInsertRequest> userRequest = new LinkedList<AdminInsertRequest>();

        if (adminService.existsByEmail("anarceferov1996@gmail.com") &&
                adminService.existsByEmail("mjafarov21@gmail.com")) {
            return;
        }
        userRequest.add(
                new AdminInsertRequest(1L,
                        "Anar",
                        "Cəfərov",
                        "İlham",
                        "anarceferov1996@gmail.com",
                        "123456"
                )
        );
        userRequest.add(
                new AdminInsertRequest(2L,
                        "Manaf",
                        "Cəfərov",
                        "İlham",
                        "mjafarov21@gmail.com",
                        "123456"
                )
        );
        adminService.saveAll(userRequest);
    }
}
