package com.teapot.mrw.billing_ne.service;

import com.teapot.mrw.billing_ne.model.Tariff;
import com.teapot.mrw.billing_ne.model.TariffOption;
import com.teapot.mrw.billing_ne.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;

    @Autowired
    public TariffServiceImpl(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    @Override
    public Tariff findTariff(long id) {
        return tariffRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tariff> findAll() {
            return tariffRepository.findAll();
    }

    @Override
    public List<Tariff> findAllByActiveTrue(){
        return tariffRepository.findAllByActiveTrue();
    }

    @Override
    public Page<Tariff> findWithPagination(Integer page, Integer tariffsPerPage) {
        return tariffRepository.findAll(PageRequest.of(page - 1, tariffsPerPage));
    }

    @Override
    public Page<Tariff> findWithPaginationByActiveTrue(Integer page, Integer tariffsPerPage) {
        return tariffRepository.findAllByActiveTrue(PageRequest.of(page - 1, tariffsPerPage));
    }

    @Override
    @Transactional
    public void update(long id, Tariff tariff) {
        Optional<Tariff> old = tariffRepository.findById(id);
        if (old.isPresent()){
            tariff.setId(id);
            tariffRepository.save(tariff);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        tariffRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(Tariff tariff) {
        tariffRepository.save(tariff);
    }

    @Override
    @Transactional
    public void release(long id, TariffOption selectedTariffOption) {
        tariffRepository.findById(id).ifPresent(
                tariff -> {
                    tariff.removeOption(selectedTariffOption);
                }
                );
    }

    @Override
    @Transactional
    public void assign(long id, TariffOption selectedTariffOption) {
        tariffRepository.findById(id).ifPresent(
                tariff -> {
                    tariff.addOption(selectedTariffOption);
                }
        );
    }
}
