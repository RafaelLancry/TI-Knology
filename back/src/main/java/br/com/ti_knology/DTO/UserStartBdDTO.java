package br.com.ti_knology.DTO;

import java.util.Date;

public record UserStartBdDTO(String name, String email, String password, String phone, String cpf, Date birthdate) {
}
