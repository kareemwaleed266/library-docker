package com.library.auth.seed;

import com.library.auth.config.AdminSeedProperties;
import com.library.auth.entity.Role;
import com.library.auth.entity.User;
import com.library.auth.enums.RoleName;
import com.library.auth.repository.RoleRepository;
import com.library.auth.repository.UserRepository;
import com.library.common.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Component
public class DataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final AdminSeedProperties adminSeedProperties;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(
            AdminSeedProperties adminSeedProperties,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.adminSeedProperties = adminSeedProperties;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!adminSeedProperties.enabled()) {
            log.info("Admin seed is disabled");
            return;
        }

        if (userRepository.countActiveUsersByRole(RoleName.ADMIN) > 0) {
            log.info("Active admin user already exists");
            return;
        }

        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseThrow(() -> new NotFoundException("Admin role is not configured"));

        String email = normalizeEmail(adminSeedProperties.email());

        userRepository.findByEmailIgnoreCase(email)
                .ifPresentOrElse(
                        existingUser -> promoteExistingUserToAdmin(existingUser, adminRole),
                        () -> createDefaultAdmin(adminRole, email)
                );
    }

    private void promoteExistingUserToAdmin(User user, Role adminRole) {
        user.setRole(adminRole);
        user.activate();

        log.info("Existing user promoted to admin: {}", user.getEmail());
    }

    private void createDefaultAdmin(Role adminRole, String email) {
        User admin = new User(
                adminSeedProperties.fullName().trim(),
                email,
                normalizePhone(adminSeedProperties.phone()),
                passwordEncoder.encode(adminSeedProperties.password()),
                adminRole
        );

        userRepository.save(admin);

        log.info("Default admin user created: {}", email);
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private String normalizePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            return null;
        }

        return phone.trim();
    }
}