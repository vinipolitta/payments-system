package com.vinipolitta.payment_system.DTO;

import com.vinipolitta.payment_system.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotNull(message = "O Nome nao pode esta vazio")
        @NotBlank(message = "O Nome nao pode esta nulo")
        @Size(min = 3, max = 150, message = "O Nome deve ter entre 3 e 150 caracteres")
        String name,

        @Email(message = "Digite um email valido")
        @NotNull(message = "O Email nao pode esta vazio")
        @NotBlank(message = "O Email nao pode esta nulo")
        String email,

        @NotNull(message = "O Senha nao pode esta vazio")
        @NotBlank(message = "O Senha nao pode esta nulo")
        @Size(min = 6, max = 150, message = "A Senha deve ter no minimo 6 e no maximo 150 caracteres")
        String password
) {

    public User toModel(){
       return new User(name, email, password);
    }
}
