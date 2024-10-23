package com.vinipolitta.payment_system.DTO;

import com.vinipolitta.payment_system.entity.User;

public record UserRequest(String name, String email, String password) {

    public User toModel(){
       return new User(name, email, password);
    }
}
