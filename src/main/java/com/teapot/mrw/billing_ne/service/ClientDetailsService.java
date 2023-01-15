package com.teapot.mrw.billing_ne.service;

import com.teapot.mrw.billing_ne.model.Client;
import com.teapot.mrw.billing_ne.repository.ClientRepository;
import com.teapot.mrw.billing_ne.security.ClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> optionalClient = clientRepository.findByEmail(username);
        if (optionalClient.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return new ClientDetails(optionalClient.get());
    }
}
