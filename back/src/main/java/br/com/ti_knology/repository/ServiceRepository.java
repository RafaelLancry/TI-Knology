package br.com.ti_knology.repository;

import br.com.ti_knology.enums.Status;
import br.com.ti_knology.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO services (name, status, price, due, deliver_date, category_id) VALUES (:name, CAST(:status AS status), :price, :due, :deliverDate, :categoryId)", nativeQuery = true)
    void insertService(@Param("name") String name,
                       @Param("status") String status,
                       @Param("price") Float price,
                       @Param("due") Integer due,
                       @Param("deliverDate") Date deliverDate,
                       @Param("categoryId") Long categoryId);

    @Query(value = "SELECT * FROM services", nativeQuery = true)
    List<Service> findAllServices();

    @Transactional
    @Modifying
    @Query(value = "UPDATE services SET status=CAST(:status AS status) where id=:id", nativeQuery = true)
    void updateServiceStatusById(@Param("id") long id, @Param("status") String status);

    public Service getById(Long id);
}