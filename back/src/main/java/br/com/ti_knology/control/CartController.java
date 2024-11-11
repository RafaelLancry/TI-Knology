package br.com.ti_knology.controller;

import br.com.ti_knology.enums.ServicesType;
import br.com.ti_knology.enums.Status;
import br.com.ti_knology.model.Cart;
import br.com.ti_knology.model.Purchase;
import br.com.ti_knology.model.Service;
import br.com.ti_knology.repository.CartRepository;
import br.com.ti_knology.repository.PurchaseRepository;
import br.com.ti_knology.repository.ServiceRepository;
import br.com.ti_knology.util.InsertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pagamento")
public class CartController {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private InsertUtils insertUtils;

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    @PostMapping("/confirmar/{userId}")
    public ResponseEntity<List<Purchase>> createPurchase(@PathVariable String userId, @RequestParam List<Long> serviceIds) {
        Cart cart = cartRepository.findByUserId(Long.parseLong(userId));

        List<Purchase> purchases = new ArrayList<>();
        for (Long serviceId : serviceIds) {
            Service referenceService = serviceRepository.findById(serviceId).get();
            LocalDate novaData = LocalDate.now();
            novaData = novaData.plusDays(referenceService.getDue());
            System.out.println(ServicesType.fromId(serviceId).name());
            Long newServiceId = insertUtils.insertService(ServicesType.fromId(serviceId).name(), Status.PRODUZINDO.name(), referenceService.getPrice(), referenceService.getDue(), java.sql.Date.valueOf(novaData), referenceService.getCategory());
            Service newService = serviceRepository.findById(newServiceId).get();
            Purchase purchase = new Purchase(cart, newService);
            purchaseRepository.save(purchase);
            purchases.add(purchase);
        }

        return ResponseEntity.ok().body(purchases);
    }
}