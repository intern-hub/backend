package com.internhub.backend.auth;

import com.internhub.backend.errors.exceptions.*;
import com.internhub.backend.models.User;
import com.internhub.backend.models.temporary.ChangePassword;
import com.internhub.backend.models.temporary.ResetPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AuthController {
    private static final String RESET_PASSWORD_LINK =
            "https://intern-hub.github.io/frontend/#/frontend/reset-password/%s";
    private static final String FORGOT_PASSWORD_EMAIL_SENDER =
            "internhub.notifier@yahoo.com";
    private static final String FORGOT_PASSWORD_EMAIL_SUBJECT =
            "InternHub - Password Reset Requested";
    private static final String FORGOT_PASSWORD_EMAIL_TEMPLATE =
            "<html><body>" +
                    "Hello %s,<br/><br/>" +
                    "If you did <i>not</i> request a password reset, please ignore this message.<br/><br/>" +
                    "Otherwise, please click <a href=\"%s\">here</a> to reset your password.<br/><br/>" +
                    "Unable to access the above link? View it directly:<br/>%s<br/><br/>" +
                    "Sincerely,<br/>" +
                    "The InternHub team" +
                    "</body></html>";

    @Autowired
    private UserRepository repository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender emailSender;

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
            throw new UserConflictException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setResetToken(null);
        repository.save(user);
    }

    @PostMapping("/auth/password/change")
    void changePassword(@RequestBody ChangePassword passwordChange, Principal principal) {
        if (passwordChange.getOldPassword() == null || passwordChange.getNewPassword() == null) {
            throw new ChangePasswordMalformedException();
        }
        User user = repository.findByUsername(principal.getName());
        if (!passwordEncoder.matches(passwordChange.getOldPassword(), user.getPassword())) {
            throw new ChangePasswordAccessDeniedException();
        }
        user.setPassword(passwordEncoder.encode(passwordChange.getNewPassword()));
        user.setResetToken(null);
        repository.save(user);
    }

    @PostMapping("/auth/password/forgot")
    void forgotPassword(@RequestBody User forgotBody) throws MessagingException {
        if (forgotBody.getUsername() == null || forgotBody.getEmail() == null) {
            throw new ForgotPasswordMalformedException();
        }

        User user = repository.findByUsername(forgotBody.getUsername());
        if (user == null || !user.getEmail().equals(forgotBody.getEmail())) {
            throw new ForgotPasswordAccessDeniedException();
        }

        // Generate a token used to identify the user; they are required to verify
        // it for the password reset to take effect
        String token = UUID.randomUUID().toString();
        String tokenLink = String.format(RESET_PASSWORD_LINK, token);

        // Set the reset password and token
        user.setResetToken(token);
        repository.save(user);

        // Send email to the user with the contents of their new password
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(FORGOT_PASSWORD_EMAIL_SENDER);
        helper.setTo(user.getEmail());
        helper.setSubject(FORGOT_PASSWORD_EMAIL_SUBJECT);
        helper.setText(String.format(
                FORGOT_PASSWORD_EMAIL_TEMPLATE,
                user.getUsername(),
                tokenLink, tokenLink
        ), true);
        emailSender.send(message);
    }

    @PostMapping("/auth/password/reset")
    void resetPassword(@RequestBody ResetPassword resetBody) {
        if (resetBody.getToken() == null) {
            throw new ResetPasswordMalformedException();
        }

        // Using the specified token, find the user associated with it
        User user = repository.findByResetToken(resetBody.getToken());
        if (user == null) {
            throw new ResetPasswordAccessDeniedException();
        }

        // Remove the reset token and set new password
        user.setPassword(passwordEncoder.encode(resetBody.getNewPassword()));
        user.setResetToken(null);
        repository.save(user);
    }
}
