package com.teapot.mrw.billing_ne.service;

import com.teapot.mrw.billing_ne.dto.SearchDTO;
import com.teapot.mrw.billing_ne.model.Client;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client findClient(long id);
    List<Client> findAll();
    Page<Client> findWithPagination(Integer page, Integer clientsPerPage);
    void update(long id, Client client);
    void delete(long id);
    void save(Client client);
    Optional<Client> getClientByPassportData(String passportData);
    List<Client> clientSearch(SearchDTO searchDTO);
}
