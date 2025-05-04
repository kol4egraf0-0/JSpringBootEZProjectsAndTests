package com.example.demo.service;

import com.example.demo.dto.LoginUserDto;
import com.example.demo.dto.RegsiterUserDto;
import com.example.demo.dto.VerifyUserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    public User signUp(RegsiterUserDto input) { //вход
        User user = new User(input.getUsername(), input.getEmail(), passwordEncoder.encode(input.getPassword()));
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);
        sendVerificationEmail(user);
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) { //аутентификация
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("Польватель не найден"));

        if(!user.isEnabled()) {
            throw new RuntimeException("Аккаунт не верифицирован!!!");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
        return user;
    }

    public void verifyUser(VerifyUserDto input) { //верификациионнаый кодик который на почт
        Optional<User> optionalUser = userRepository.findByEmail(input.getEmail());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Срок верификационного кода истек!!!");
            }
            if (user.getVerificationCode().equals(input.getVerificationCode())) { //ввел верификационный код
                user.setEnabled(true);
                user.setVerificationCode(null); //тк ввел
                user.setVerificationCodeExpiresAt(null);
                userRepository.save(user);
            } else{
                throw new RuntimeException("Недействительный код");
            }
        } else {
            throw new RuntimeException("Пользователь не найден");
        }
    }

    public void resendVerificationCode(String email) { //реотпавка
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.isEnabled()) {
                throw new RuntimeException("Пользователь уже подтвердился");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Пользователь не найден");
        }
    }


    public void sendVerificationEmail(User user) {
        String subject = "Верификация Аккаунта";
        String verificationCode = "ВЕРИФИКАЦИОННЫЙ КОД " + user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        try {
            System.out.println("[INFO] Attempting to send verification email to: " + user.getEmail());

            emailService.sendVerificationEmail(
                    user.getEmail(),
                    subject,
                    htmlMessage
            );

            System.out.println("[INFO] Verification email successfully sent to: " + user.getEmail());

        } catch (MessagingException e) {
            System.out.println("[ERROR] Failed to send email to " + user.getEmail());
            e.printStackTrace();

            if (e.getMessage().contains("Invalid Addresses")) {
                System.out.println("[WARN] Invalid email format: " + user.getEmail());
            }
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
