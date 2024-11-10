package br.com.ti_knology.control;

import br.com.ti_knology.DTO.UserConnectionDTO;
import br.com.ti_knology.DTO.UserInfoDTO;
import br.com.ti_knology.DTO.UserProfileDTO;
import br.com.ti_knology.DTO.UserUpdateDTO;
import br.com.ti_knology.model.Cart;
import br.com.ti_knology.model.Purchase;
import br.com.ti_knology.model.Service;
import br.com.ti_knology.model.User;
import br.com.ti_knology.repository.CartRepository;
import br.com.ti_knology.repository.PurchaseRepository;
import br.com.ti_knology.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    CartRepository cartRepository;

    @GetMapping("/login")
    public ResponseEntity<UserInfoDTO> loginUser(@RequestParam String email, @RequestParam String password){
        User foundUser = userRepository.getUserByEmailContaining(email);
        if(foundUser == null){
            return null;
        }else{
            if(foundUser.getPassword().equals(password)){
                var response = new UserInfoDTO(foundUser.getName(), foundUser.getPhone(), foundUser.getEmail(), foundUser.getCpf(), foundUser.getId());
                return ResponseEntity.ok().body(response);
            }else {
                return null;
            }
        }
    }

    @GetMapping
    public ResponseEntity<UserConnectionDTO> loginUser(@RequestParam String idReceived){
        Long id = Long.parseLong(idReceived);
        User foundUser = userRepository.getUserById(id);
        if(foundUser == null){
            return null;
        }else{
               return ResponseEntity.ok().body(new UserConnectionDTO(foundUser.getName(), foundUser.getPhone(), foundUser.getEmail()));
        }
    }

    @PostMapping("/registro")
    public void registerUser(@RequestBody UserUpdateDTO user) {
        userRepository.save(new User(user));
    }

    @GetMapping("/perfil")
    public ResponseEntity<UserProfileDTO> perfilUser(@RequestParam String idReceived){
        Long id = Long.parseLong(idReceived);
        User foundUser = userRepository.getUserById(id);
        if(foundUser == null){
            return null;
        }else{
            return ResponseEntity.ok().body(new UserProfileDTO(foundUser.getName(), foundUser.getPhone(), foundUser.getEmail(), foundUser.getBirthdate()));
        }
    }

    @GetMapping("/perfil/biblioteca")
    public ResponseEntity<Object[]> bibliotecaUser(@RequestParam String idReceived){
        Cart cart = cartRepository.findByUserId(Long.parseLong(idReceived));
        List<Service> servicesAdquiridos = new java.util.ArrayList<>(List.of());
        List<Purchase> compras = purchaseRepository.findAllByCart(cart);
        Purchase[] comprasArray = new Purchase[compras.size()];
        compras.toArray(comprasArray);
        for(Purchase compra : comprasArray){
            servicesAdquiridos.add(compra.getService());
        }

        //List<ServicePurchases> servicePurchases
        return ResponseEntity.ok().body(servicesAdquiridos.toArray());
    }
}
