package com.teapot.mrw.billing_ne.repository;

import com.teapot.mrw.billing_ne.model.Tariff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TariffRepository  extends JpaRepository<Tariff, Long> {
    Page<Tariff> findAllByActiveTrue(Pageable pageable);
    List<Tariff> findAllByActiveTrue();
}
