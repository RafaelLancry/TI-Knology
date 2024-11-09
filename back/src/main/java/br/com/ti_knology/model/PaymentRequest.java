package br.com.ti_knology.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private List<Service> services;
    private PaymentDetails paymentDetails;
    private Long userId;
}