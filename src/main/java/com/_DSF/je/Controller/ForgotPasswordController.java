package com._DSF.je.Controller;

import com._DSF.je.Entity.User;
import com._DSF.je.Repository.UserRepository;
import com._DSF.je.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class ForgotPasswordController {

    private final  UserRepository userRepository;

    private final EmailService emailService;

    public ForgotPasswordController(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        // Find user by email
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            // Get user's password
            String password = userOptional.get().getPassword();

            // Send password via email
            emailService.sendEmail(email, "Your Password", "Your password is: " + password);

            return ResponseEntity.ok("Password has been sent to your email.");
        } else {
            return ResponseEntity.status(404).body("Email not found.");
        }
    }
}
