package com.teapot.mrw.billing_ne.repository;

import com.teapot.mrw.billing_ne.model.TariffOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffOptionRepository  extends JpaRepository<TariffOption, Long> {
}
