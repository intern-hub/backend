package com.internhub.backend.auth;

import com.internhub.backend.errors.exceptions.UserConflictException;
import com.internhub.backend.errors.exceptions.UserMalformedException;
import com.internhub.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/auth/me")
    @ResponseBody
    User getMe(Principal principal) {
        String username = principal.getName();
        return repository.findByUsername(username);
    }

    @PostMapping("/auth/signup")
    void signup(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null || user.getUsername() == null) {
            throw new UserMalformedException();
        }
        String username = user.getUsername();
        if (repository.findByUsername(username) != null) {
            throw new UserConflictException(username);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
}
