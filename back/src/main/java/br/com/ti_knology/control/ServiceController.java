package br.com.ti_knology.control;


import br.com.ti_knology.DTO.ServicePayment;
import br.com.ti_knology.DTO.ServicesRequestsDTO;
import br.com.ti_knology.config.ServiceRequestCreation;
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
    public ResponseEntity<List<ServicesRequestsDTO>> getAllServices() {
        List<ServicesRequestsDTO> services = ServiceRequestCreation.getAllServicesTypes();

        return ResponseEntity.ok(services);
    }
}
