package com.vinipolitta.payment_system.service;

import com.vinipolitta.payment_system.entity.User;
import com.vinipolitta.payment_system.repository.UserRepository;
import com.vinipolitta.payment_system.utils.RandonString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passWordEncoder;

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
             throw new RuntimeException("This email is already registered. Please choose a different one.");
        } else {
            String encodedPassword = passWordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String randonCode = RandonString.generateRandomString(64);
            user.setVerificationCode(randonCode);

            user.setEnabled(false);
            User saveduser = userRepository.save(user);

            return saveduser;
        }
    }
}
