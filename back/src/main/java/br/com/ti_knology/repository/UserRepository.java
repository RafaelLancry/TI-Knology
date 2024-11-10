package br.com.ti_knology.repository;

import br.com.ti_knology.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User getUserByEmailContaining(String email);

    public User getUserById(Long id);
}
