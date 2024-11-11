package br.com.ti_knology.DTO;

import java.util.Date;

public record ServiceProfileSendDTO(Date deliverDate, String name, String status, Float price, int due) {
}
