package com.teapot.mrw.billing_ne.repository;

import com.teapot.mrw.billing_ne.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository  extends JpaRepository<Contract, Long> {
    Optional<List<Contract>> findByNumberIsStartingWith(String query);
}
