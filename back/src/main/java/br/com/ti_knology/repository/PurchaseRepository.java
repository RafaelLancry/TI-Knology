package br.com.ti_knology.repository;

import br.com.ti_knology.model.Cart;
import br.com.ti_knology.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}