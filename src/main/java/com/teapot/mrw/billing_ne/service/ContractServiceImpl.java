package com.teapot.mrw.billing_ne.service;

import com.teapot.mrw.billing_ne.model.Contract;
import com.teapot.mrw.billing_ne.model.Tariff;
import com.teapot.mrw.billing_ne.model.TariffOption;
import com.teapot.mrw.billing_ne.repository.ContractRepository;
import com.teapot.mrw.billing_ne.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final TariffRepository tariffRepository;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, TariffRepository tariffRepository) {
        this.contractRepository = contractRepository;
        this.tariffRepository = tariffRepository;
    }

    @Override
    public Contract findContract(long id) {
        return contractRepository.findById(id).orElse(null);
    }

    @Override
    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    @Override
    public Page<Contract> findWithPagination(Integer page, Integer contractsPerPage) {
        return contractRepository.findAll(PageRequest.of(page - 1, contractsPerPage));
    }

    @Override
    @Transactional
    public void update(long id, Contract contract) {
        Optional<Contract> old = contractRepository.findById(id);
        if (old.isPresent()){
            contract.setId(id);
            contractRepository.save(contract);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        contractRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(Contract contract) {
        contractRepository.save(contract);
    }

    @Override
    @Transactional
    public void changeTariff(long id, Tariff selectedTariff) {
        contractRepository.findById(id).ifPresent(
                contract -> {
                    contract.setTariff(selectedTariff);
                    contract.setTariffOptionList(updateTariffOptionAfterChangedTariff(contract.getTariffOptionList(), selectedTariff.getId()));
                }
        );
    }

    @Override
    @Transactional
    public void releaseTariffOptions(long id, TariffOption selectedTariffOption) {
        contractRepository.findById(id).ifPresent(
                contract -> {
                    contract.removeOption(selectedTariffOption);
                }
        );
    }

    @Override
    @Transactional
    public void assignTariffOptions(long id, TariffOption selectedTariffOption) {
        contractRepository.findById(id).ifPresent(
                contract -> {
                    contract.addOption(selectedTariffOption);
                }
        );
    }

    @Override
    @Transactional
    public void blocking(long id) {
        contractRepository.findById(id).ifPresent(
                contract -> {
                    contract.setActive(false);
                }
        );
    }

    @Override
    @Transactional
    public void unlocking(long id) {
        contractRepository.findById(id).ifPresent(
                contract -> {
                    contract.setActive(true);
                }
        );
    }

    @Override
    public List<Contract> findByNumber(String query){
        return contractRepository.findByNumberIsStartingWith(query).orElse(Collections.emptyList());
    }

    private List<TariffOption> updateTariffOptionAfterChangedTariff(List<TariffOption> tariffOptionListFromContract, Long tariffID){
        List<TariffOption> newListTariffOptionForContract = new ArrayList<>(tariffOptionListFromContract);
        Optional<Tariff> tariffOptional = tariffRepository.findById(tariffID);
        if (tariffOptional.isPresent()){
            Tariff tariff = tariffOptional.get();
            List<TariffOption> tariffOptionListFromTariff = tariff.getTariffOptionList();
            newListTariffOptionForContract.retainAll(tariffOptionListFromTariff);
        }
        return newListTariffOptionForContract;
    }
}
