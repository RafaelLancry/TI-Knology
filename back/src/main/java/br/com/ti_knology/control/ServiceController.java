package br.com.ti_knology.control;


import br.com.ti_knology.enums.Status;
import br.com.ti_knology.model.*;
import br.com.ti_knology.repository.CartRepository;
import br.com.ti_knology.repository.ServiceRepository;
import br.com.ti_knology.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("services")
public class ServiceController {
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        List<Service> services = serviceRepository.findAllServices();
        return ResponseEntity.ok(services);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/payment")
    @Transactional
    public ResponseEntity<Service[]> processPayment(@RequestBody PaymentRequest paymentRequest) {
        Service[] servicesArray = new Service[8];
        int count = 0;
        List<Service> services = paymentRequest.getServices();
        System.out.println(services);
        
        for (Service service : services) {
            createCart(paymentRequest.getUserId(), service);
            servicesArray[count] = serviceRepository.getById(service.getId());
            serviceRepository.updateServiceStatusById(servicesArray[count].getId(),Status.EM_ELABORACAO.getValue().replace(" ", "_"));
            count++;
        }

        return ResponseEntity.ok().body(servicesArray);
    }

    private void createCart(Long userId, Service service) {
        User user = userRepository.getUserById(userId);
        if (user != null && service != null) {
            cartRepository.save(new Cart(user, service));
        }

    }

}
