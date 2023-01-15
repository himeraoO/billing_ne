package com.teapot.mrw.billing_ne.repository;

import com.teapot.mrw.billing_ne.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByPassportData(String passportData);
    Optional<Client> findByEmail(String email);

    @Query("SELECT DISTINCT c FROM Client c INNER JOIN c.contractList cl WHERE cl.number LIKE CONCAT(:query, '%')")
    Optional<List<Client>> clientSearch(String query);
}
