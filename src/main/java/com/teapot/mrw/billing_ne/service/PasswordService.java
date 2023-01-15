package com.teapot.mrw.billing_ne.service;

import com.teapot.mrw.billing_ne.dto.PasswordDTO;
import com.teapot.mrw.billing_ne.model.Client;
import com.teapot.mrw.billing_ne.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PasswordService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void updatePassword(Long id, PasswordDTO passwordDTO){
        Optional<Client> old = clientRepository.findById(id);
        if (old.isPresent()){
            Client client = old.get();
            client.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
            clientRepository.save(client);
        }
    }
}
