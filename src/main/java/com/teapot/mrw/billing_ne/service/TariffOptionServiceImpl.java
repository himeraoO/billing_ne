package com.teapot.mrw.billing_ne.service;

import com.teapot.mrw.billing_ne.model.TariffOption;
import com.teapot.mrw.billing_ne.repository.TariffOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TariffOptionServiceImpl implements TariffOptionService {

    final TariffOptionRepository tariffOptionRepository;

    @Autowired
    public TariffOptionServiceImpl(TariffOptionRepository tariffOptionRepository) {
        this.tariffOptionRepository = tariffOptionRepository;
    }

    @Override
    public TariffOption findTariffOption(long id) {
        return tariffOptionRepository.findById(id).orElse(null);
    }

    @Override
    public List<TariffOption> findAll() {
        return tariffOptionRepository.findAll();
    }

    @Override
    public Page<TariffOption> findWithPagination(Integer page, Integer tariffOptionsPerPage) {
        return tariffOptionRepository.findAll(PageRequest.of(page - 1, tariffOptionsPerPage));
    }

    @Override
    @Transactional
    public void update(long id, TariffOption tariffOption) {
        Optional<TariffOption> old = tariffOptionRepository.findById(id);
        if (old.isPresent()){
            tariffOption.setId(id);
            tariffOptionRepository.save(tariffOption);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        tariffOptionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(TariffOption tariffOption) {
        tariffOptionRepository.save(tariffOption);
    }
}
