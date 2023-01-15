package com.teapot.mrw.billing_ne.service;

import com.teapot.mrw.billing_ne.model.TariffOption;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TariffOptionService {
    TariffOption findTariffOption(long id);
    List<TariffOption> findAll();
    Page<TariffOption> findWithPagination(Integer page, Integer tariffOptionsPerPage);
    void update(long id, TariffOption tariffOption);
    void delete(long id);
    void save(TariffOption tariffOption);
}
