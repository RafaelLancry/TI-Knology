package br.com.ti_knology.control;

import br.com.ti_knology.DTO.*;
import br.com.ti_knology.enums.ServicesType;
import br.com.ti_knology.model.Cart;
import br.com.ti_knology.model.Purchase;
import br.com.ti_knology.model.User;
import br.com.ti_knology.repository.CartRepository;
import br.com.ti_knology.repository.PurchaseRepository;
import br.com.ti_knology.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
            if(cartRepository.findByUserId(foundUser.getId()) == null){
                cartRepository.save(new Cart(foundUser));
            }

            if(foundUser.getPassword().equals(password)){
                var response = new UserInfoDTO(foundUser.getName(), foundUser.getPhone(), foundUser.getEmail(), foundUser.getCpf(), foundUser.getId());
                return ResponseEntity.ok().body(response);
            }else {
                return null;
            }
        }
    }

    @GetMapping
    public ResponseEntity<UserConnectionDTO> confirmLogin(@RequestParam String idReceived){
        Long id = Long.parseLong(idReceived);
        User foundUser = userRepository.getUserById(id);
        if(foundUser == null){
            return null;
        }else{
               return ResponseEntity.ok().body(new UserConnectionDTO(foundUser.getName(), foundUser.getPhone(), foundUser.getEmail()));
        }
    }

    @Transactional
    @PostMapping("/registro")
    public void registerUser(@RequestBody UserStartBdDTO user) {
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
    public ResponseEntity<ServiceProfileSendDTO[]> bibliotecaUser(@RequestParam String idReceived){
        Cart cart = cartRepository.findByUserId(Long.parseLong(idReceived));
        List<ServiceProfileSendDTO> servicesAdquiridos = new java.util.ArrayList<ServiceProfileSendDTO>();
        List<Purchase> compras = purchaseRepository.findAllByCart(cart);
        Purchase[] comprasArray = new Purchase[compras.size()];
        compras.toArray(comprasArray);
        for(Purchase compra : comprasArray){
            ServiceProfileSendDTO addService = new ServiceProfileSendDTO(
                    compra.getService().getDeliverDate(),
                    ServicesType.fromRawValue(compra.getService().getName()).getValue(),
                    compra.getService().getStatus(), compra.getService().getPrice(), compra.getService().getDue());

            servicesAdquiridos.add(addService);
        }

        //List<ServicePurchases> servicePurchases
        return ResponseEntity.ok().body(servicesAdquiridos.toArray(new ServiceProfileSendDTO[0]));
    }

    @Transactional
    @PutMapping("/atualizar")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDTO user, @RequestParam String userId){
        Long id = Long.parseLong(userId);
        User foundUser = userRepository.getUserById(id);
        if(foundUser == null){
            return null;
        }else {
            foundUser.atualizarInformacoes(user);
        }

        return ResponseEntity.ok().body("Edição realizada com sucesso!");
    }

    @Transactional
    @PutMapping("/trocarSenha")
    public ResponseEntity<String> updateUser(@RequestBody UserPasswordUpdateDTO user, @RequestParam String idReceived){
        Long id = Long.parseLong(idReceived);
        User foundUser = userRepository.getUserById(id);
        if(foundUser == null){
            return null;
        }else {
            int req = foundUser.atualizarSenha(user);
            if(req == 0){
                return ResponseEntity.badRequest().body("Houve uma falha na edição.");
            }else{
                return ResponseEntity.ok().body("Edição realizada com sucesso!");
            }
        }
    }

    @Transactional
    @DeleteMapping("/deletar")
    public ResponseEntity<String> deleteUser(@RequestParam String idReceived){
        Long id = Long.parseLong(idReceived);
        User foundUser = userRepository.getUserById(id);
        Cart cart = cartRepository.findByUserId(id);
        if(foundUser == null){
            return null;
        }else {
            cartRepository.delete(cart);
            userRepository.delete(foundUser);
        }
        return ResponseEntity.ok().body("Edição realizada com sucesso!");
    }
}