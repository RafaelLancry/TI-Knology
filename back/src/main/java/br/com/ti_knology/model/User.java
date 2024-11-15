package br.com.ti_knology.model;

import br.com.ti_knology.DTO.UserPasswordUpdateDTO;
import br.com.ti_knology.DTO.UserStartBdDTO;
import br.com.ti_knology.DTO.UserUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String cpf;

    private Date birthdate;

    @Column(unique = true)
    private String phone;

    public User(UserStartBdDTO userData){
        this.cpf = userData.cpf();
        this.name = userData.name();
        this.email = userData.email();
        this.phone = userData.phone();
        this.birthdate = userData.birthdate();
        this.password = userData.password();
    }

    public void atualizarInformacoes(UserUpdateDTO update){
        if(!this.name.equals(update.name())){
            this.name = update.name();
        }
        if(!this.email.equals(update.email())){
            this.email = update.email();
        }

        if(!this.phone.equals(update.phone())){
            this.phone = update.phone();
        }
    }

    public int atualizarSenha(UserPasswordUpdateDTO update){
        if (this.password.equals(update.currentPassword())){
            this.password = update.newPassword();
            return 1;
        }else{
            return 0;
        }
    }
}
