package com.teapot.mrw.billing_ne.service;

import com.teapot.mrw.billing_ne.dto.SearchDTO;
import com.teapot.mrw.billing_ne.model.Client;
import com.teapot.mrw.billing_ne.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client findClient(long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Page<Client> findWithPagination(Integer page, Integer clientsPerPage) {
        return clientRepository.findAll(PageRequest.of(page - 1, clientsPerPage));
    }

    @Override
    @Transactional
    public void update(long id, Client client) {
        Optional<Client> old = clientRepository.findById(id);
        if (old.isPresent()){
            Client clientOld = old.get();
            client.setPassword(clientOld.getPassword());
            client.setContractList(clientOld.getContractList());
            client.setRole(clientOld.getRole());
            clientRepository.save(client);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
    }

    @Override
    public Optional<Client> getClientByPassportData(String passportData) {
        return clientRepository.findByPassportData(passportData);
    }

    @Override
    public List<Client> clientSearch(SearchDTO searchDTO) {
        String query = searchDTO.getNumber();
        return clientRepository.clientSearch(query).orElse(Collections.emptyList());
    }

}
