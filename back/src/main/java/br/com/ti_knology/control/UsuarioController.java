package br.com.ti_knology.control;

import br.com.ti_knology.DTO.UserConnectionDTO;
import br.com.ti_knology.DTO.UserInfoDTO;
import br.com.ti_knology.DTO.UserUpdateDTO;
import br.com.ti_knology.model.User;
import br.com.ti_knology.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    UserRepository userRepository;

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

}
