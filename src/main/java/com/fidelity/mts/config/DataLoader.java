package com.fidelity.mts.config;

import com.fidelity.mts.repo.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Seeds username table with BCrypt-encoded passwords if empty.
 * Passwords: alice123, bob456, carol789 (id matches account.id).
 */
@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadUsers(UserRepository userRepository, JdbcTemplate jdbcTemplate,
                                       PasswordEncoder encoder) {
        return args -> {
            if (userRepository.count() > 0) {
                return;
            }
            String sql = "INSERT INTO username (id, username, password) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, 1L, "alice", encoder.encode("alice123"));
            jdbcTemplate.update(sql, 2L, "bob", encoder.encode("bob456"));
            jdbcTemplate.update(sql, 3L, "carol", encoder.encode("carol789"));
        };
    }
}
