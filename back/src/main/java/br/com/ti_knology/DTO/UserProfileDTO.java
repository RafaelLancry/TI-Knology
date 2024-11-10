package br.com.ti_knology.DTO;

import java.util.Date;

public record UserProfileDTO(String name, String phone, String email, Date birthday) {
}