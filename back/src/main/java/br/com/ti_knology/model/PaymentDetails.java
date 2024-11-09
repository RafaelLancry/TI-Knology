package br.com.ti_knology.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {
    private String cardNumber;
    private String cardName;
    private String cardCVC;
}