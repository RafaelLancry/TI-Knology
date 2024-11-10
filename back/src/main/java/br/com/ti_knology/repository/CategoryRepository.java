package br.com.ti_knology.repository;

import br.com.ti_knology.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name); // Método para encontrar uma categoria pelo nome
}