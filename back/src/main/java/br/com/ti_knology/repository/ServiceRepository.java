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

    @Query(value = "SELECT * FROM service", nativeQuery = true)
    List<Service> findAllServices();

    @Transactional
    @Modifying
    @Query(value = "UPDATE service SET status=CAST(:status AS status) where id=:id", nativeQuery = true)
    void updateServiceStatusById(@Param("id") long id, @Param("status") String status);

    void findById(long id);
}