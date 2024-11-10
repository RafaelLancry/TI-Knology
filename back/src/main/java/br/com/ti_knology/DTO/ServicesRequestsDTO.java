package br.com.ti_knology.DTO;

import br.com.ti_knology.enums.ServicesType;
import br.com.ti_knology.enums.Status;

import java.io.Serializable;
import java.util.Date;

public record ServicesRequestsDTO(Long id, String name, Status status, Float price, int due, Date deliverDate){
}
