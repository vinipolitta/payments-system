package com.vinipolitta.payment_system.controller;


import com.vinipolitta.payment_system.DTO.UserRequest;
import com.vinipolitta.payment_system.DTO.UserResponse;
import com.vinipolitta.payment_system.entity.User;
import com.vinipolitta.payment_system.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) throws MessagingException, UnsupportedEncodingException {
        User user = userRequest.toModel();
        UserResponse userSaved = userService.registerUser(user);
        return ResponseEntity.ok().body(userSaved);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        return userService.verify(code) ?
                "Verification successful" :
                "Invalid verification code";

    }
}
