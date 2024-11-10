package br.com.ti_knology.repository;

import br.com.ti_knology.model.Cart;
import br.com.ti_knology.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    public List<Purchase> findAllByCart(Cart cart);
}