package com.jafarov.quiz.common.config;

import com.jafarov.quiz.admin.entity.User;
import com.jafarov.quiz.admin.service.CustomUserDetails;
import com.jafarov.quiz.admin.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareConfig implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || "anonymousUser".equals(auth.getPrincipal())) {
            return Optional.empty();
        }

        Object principal = auth.getPrincipal();

        // A) Auditoru LONG saxlamaq istəyirsinizsə ↓↓↓
        if (principal instanceof CustomUserDetails cud) {
            return Optional.of(cud.getId());     // DB sorğusu YOXDUR
        }

        // B) Yalnız email saxlamaq kifayətdirsə (auditor tipini String edin)
        // return Optional.of(auth.getName());

        return Optional.empty();
    }

}
