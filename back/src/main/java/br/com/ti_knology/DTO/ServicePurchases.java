package br.com.ti_knology.DTO;

import br.com.ti_knology.enums.Status;

import java.util.Date;

public record ServicePurchases(String name, Status status, int due, Date deliveDate) {
}
