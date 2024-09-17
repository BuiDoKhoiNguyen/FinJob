package com.rs.authenticationservice.config;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rs.authenticationservice.entity.Role;
import com.rs.authenticationservice.entity.User;
import com.rs.authenticationservice.enums.RoleEnum;
import com.rs.authenticationservice.repository.RoleRepository;
import com.rs.authenticationservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppInitConfigurer {
    @Autowired
    PasswordEncoder passwordEncoder;

    static String ADMIN_USERNAME = "admin";
    static String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Application initialization...");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
                log.info("Create admin user");
                roleRepository.save(Role.builder()
                        .name(RoleEnum.USER.getName())
                        .description("User role")
                        .build());

                Role adminRole = roleRepository.save(Role.builder()
                        .name(RoleEnum.ADMIN.getName())
                        .description("Admin role")
                        .build());

                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .username(ADMIN_USERNAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("Admin user created with username: {} and password: {}", ADMIN_USERNAME, ADMIN_PASSWORD);
            }
            log.info("Application initialization completed");
        };
    }
}
