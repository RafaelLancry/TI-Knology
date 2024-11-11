package br.com.ti_knology.util;

import br.com.ti_knology.model.Category;
import br.com.ti_knology.model.Service;
import br.com.ti_knology.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InsertUtils {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    public Long insertService(String name, String status, Float price, Integer due, Date deliverDate, Category category) {
        Service service = new Service();
        service.setName(name);
        service.setStatus(status);
        service.setPrice(price);
        service.setDue(due);
        service.setDeliverDate(deliverDate);
        service.setCategory(category); // Atribui a categoria encontrada

        // Salva o serviço e retorna o ID gerado
        Service savedService = serviceRepository.save(service);
        return savedService.getId(); // Retorna o ID do serviço inserido
    }
}
