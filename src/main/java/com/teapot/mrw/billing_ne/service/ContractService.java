package com.teapot.mrw.billing_ne.service;

import com.teapot.mrw.billing_ne.model.Contract;
import com.teapot.mrw.billing_ne.model.Tariff;
import com.teapot.mrw.billing_ne.model.TariffOption;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContractService {
    Contract findContract(long id);
    List<Contract> findAll();
    Page<Contract> findWithPagination(Integer page, Integer contractsPerPage);
    void update(long id, Contract contract);
    void delete(long id);
    void save(Contract contract);
    void changeTariff(long id, Tariff selectedTariff);
    void releaseTariffOptions(long id, TariffOption selectedTariffOption);
    void assignTariffOptions(long id, TariffOption selectedTariffOption);
    void blocking(long id);
    void unlocking(long id);
    List<Contract> findByNumber(String query);
}
