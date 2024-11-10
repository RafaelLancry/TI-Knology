package br.com.ti_knology.controller;

import br.com.ti_knology.model.Cart;
import br.com.ti_knology.model.Purchase;
import br.com.ti_knology.model.PurchaseId;
import br.com.ti_knology.model.Service;
import br.com.ti_knology.repository.CartRepository;
import br.com.ti_knology.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping("/{cartId}/purchase")
    public ResponseEntity<List<Purchase>> createPurchase(@PathVariable Long cartId, @RequestParam List<Long> serviceIds) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        List<Purchase> purchases = new ArrayList<>();
        for (Long serviceId : serviceIds) {
            Service service = serviceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));

            PurchaseId purchaseId = new PurchaseId(cartId, serviceId);
            Purchase purchase = new Purchase(purchaseId, cart, service);
            purchases.add(purchase);
        }

        // Salva todas as compras no banco
        for (Purchase purchase : purchases) {
            cart.getPurchases().add(purchase); // Adiciona a compra ao carrinho
        }
        cartRepository.save(cart); // Salva o carrinho com as compras

        return ResponseEntity.ok(purchases); // Retorna as compras criadas
    }
}