package com.teapot.mrw.billing_ne.service;

import com.teapot.mrw.billing_ne.model.Tariff;
import com.teapot.mrw.billing_ne.model.TariffOption;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TariffService {
    Tariff findTariff(long id);
    List<Tariff> findAll();
    List<Tariff> findAllByActiveTrue();
    Page<Tariff> findWithPagination(Integer page, Integer tariffsPerPage);
    Page<Tariff> findWithPaginationByActiveTrue(Integer page, Integer tariffsPerPage);
    void update(long id, Tariff tariff);
    void delete(long id);
    void save(Tariff tariff);
    void release(long id, TariffOption selectedTariffOption);
    void assign(long id, TariffOption selectedTariffOption);
}
