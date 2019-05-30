package com.internhub.backend.auth;

import com.internhub.backend.errors.exceptions.*;
import com.internhub.backend.models.User;
import com.internhub.backend.models.temporary.ChangePassword;
import com.internhub.backend.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.Principal;
import java.security.SecureRandom;

@RestController
@RequestMapping("/api")
public class AuthController {
    private static final String ALPHANUMERIC = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
    private static final int MIN_TMP_PASSWORD_LENGTH = 8;
    private static final int MAX_TMP_PASSWORD_LENGTH = 16;

    @Autowired
    private UserRepository repository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender emailSender;

    private SecureRandom secureRandom;

    public AuthController() {
        this.secureRandom = new SecureRandom();
    }

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

    @PostMapping("/auth/password/change")
    void changePassword(@RequestBody ChangePassword passwordChange, Principal principal) {
        if (passwordChange.getOldPassword() == null || passwordChange.getNewPassword() == null) {
            throw new ChangePasswordMalformedException();
        }
        User user = repository.findByUsername(principal.getName());
        if (!user.getPassword().equals(passwordEncoder.encode(passwordChange.getOldPassword()))) {
            throw new ChangePasswordAccessDeniedException();
        }
        user.setPassword(passwordEncoder.encode(passwordChange.getNewPassword()));
        repository.save(user);
    }

    @PostMapping("/auth/password/forgot")
    void forgotPassword(@RequestBody User forgotUser) throws MessagingException {
        if (forgotUser.getUsername() == null || forgotUser.getEmail() == null) {
            throw new ForgotPasswordMalformedException();
        }

        User user = repository.findByUsername(forgotUser.getUsername());
        if (user == null || !user.getEmail().equals(forgotUser.getEmail())) {
            throw new ForgotPasswordAccessDeniedException();
        }

        // Generate a new, securely random password for the user to use temporarily
        // They will want to change this password once they log in
        RandomString generator = new RandomString(MAX_TMP_PASSWORD_LENGTH, secureRandom, ALPHANUMERIC);
        int passwordLength = secureRandom.nextInt(MAX_TMP_PASSWORD_LENGTH - MIN_TMP_PASSWORD_LENGTH + 1) + MIN_TMP_PASSWORD_LENGTH;
        String newPassword = generator.nextString().substring(0, passwordLength);
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);

        // Send email to the user with the contents of their new password
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("internhub.notifier@yahoo.com");
        helper.setTo(user.getEmail());
        helper.setSubject("Your new password is ready!");
        helper.setText(String.format(
                "<html><body>Your password has been changed to <b>%s</b>.\n" +
                "Make sure to change it once you log in.</body></html>",
                newPassword
        ));
        emailSender.send(message);
    }
}
