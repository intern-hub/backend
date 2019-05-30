package com.internhub.backend.auth;

import com.internhub.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByResetToken(String token);
}
