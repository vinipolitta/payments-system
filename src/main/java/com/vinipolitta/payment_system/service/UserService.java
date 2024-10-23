package com.vinipolitta.payment_system.service;

import com.vinipolitta.payment_system.DTO.UserResponse;
import com.vinipolitta.payment_system.entity.User;
import com.vinipolitta.payment_system.repository.UserRepository;
import com.vinipolitta.payment_system.utils.RandonString;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passWordEncoder;

    @Autowired
    private EmailService emailService;

    public UserResponse registerUser(User user) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("This email is already registered. Please choose a different one.");
        } else {
            String encodedPassword = passWordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String randonCode = RandonString.generateRandomString(64);
            user.setVerificationCode(randonCode);

            user.setEnabled(false);
            User saveduser = userRepository.save(user);

            UserResponse userResponse = new UserResponse(saveduser.getUserId(), saveduser.getName(), saveduser.getEmail(), saveduser.getPassword());

            emailService.sendVerificationEmail(user);
            return userResponse;
        }
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user == null || user.isEnabled()) {
            return false;
        }
        user.setVerificationCode(null);
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }
}
