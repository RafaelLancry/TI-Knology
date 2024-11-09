package br.com.ti_knology.DTO;

import org.springframework.http.ResponseEntity;

public record UserInfoDTO(String name, String phone, String birthdate, String cpf, Long id) {

}
