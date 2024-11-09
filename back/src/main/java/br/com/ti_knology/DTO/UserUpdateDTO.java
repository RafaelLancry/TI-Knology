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
        String password,

        @NotBlank
        String phone,

        @NotBlank
        @Pattern(regexp="\\d{11}")
        String cpf,

        @NotBlank
        Date birthdate) {
}
