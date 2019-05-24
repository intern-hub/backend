package com.internhub.backend.auth;

import com.internhub.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/auth/signup")
    public void signup(@RequestBody User user) {
        if (!repository.findByUsername(user.getUsername()).isEmpty()) {
            throw new UserConflictException(user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
}
