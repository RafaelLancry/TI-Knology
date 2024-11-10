package br.com.ti_knology.control;


import br.com.ti_knology.DTO.ServicePayment;
import br.com.ti_knology.model.*;
import br.com.ti_knology.repository.CartRepository;
import br.com.ti_knology.repository.ServiceRepository;
import br.com.ti_knology.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    public ResponseEntity<Service[]> processPayment(@RequestBody ServicePayment body) {
        String[] serviceArray = body.servicesId();
        String userId = body.userId();
        Service[] services = new Service[serviceArray.length];
        System.out.println(Arrays.toString(serviceArray));
        for (int i = 0; i < serviceArray.length; i++) {
            Service service = serviceRepository.findById(Long.parseLong(serviceArray[i])).get();
            services[i] = service;
        }

        System.out.println(Arrays.toString(services));


        return ResponseEntity.ok().body(services);
    }


}
