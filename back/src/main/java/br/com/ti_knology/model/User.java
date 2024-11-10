package br.com.ti_knology.model;

import br.com.ti_knology.DTO.UserUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    private String name;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String cpf;

    private Date birthdate;

    @Column(unique = true)
    private String phone;

    public User(UserUpdateDTO userData){
        this.cpf = userData.cpf();
        this.name = userData.name();
        this.email = userData.email();
        this.phone = userData.phone();
        this.birthdate = userData.birthdate();
        this.password = userData.password();
    }
}
