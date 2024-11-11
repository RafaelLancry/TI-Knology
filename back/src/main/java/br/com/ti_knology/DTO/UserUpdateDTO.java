package br.com.ti_knology.DTO;

import jakarta.validation.constraints.*;

import java.util.Date;

public record UserUpdateDTO(
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String phone
){}
