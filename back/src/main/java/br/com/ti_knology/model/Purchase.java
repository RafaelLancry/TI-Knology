package br.com.ti_knology.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "purchase")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "purchase_id")
@Getter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchase_id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    public Purchase(Cart cart, Service service) {
        this.cart = cart;
        this.service = service;
    }
}