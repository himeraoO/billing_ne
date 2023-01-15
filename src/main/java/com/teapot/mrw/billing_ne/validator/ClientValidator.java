package com.teapot.mrw.billing_ne.validator;

import com.teapot.mrw.billing_ne.model.Client;
import com.teapot.mrw.billing_ne.service.ClientDetailsService;
import com.teapot.mrw.billing_ne.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ClientValidator implements Validator {

    private final ClientService clientService;
    private final ClientDetailsService clientDetailsService;

    @Autowired
    public ClientValidator(ClientService clientService, ClientDetailsService clientDetailsService) {
        this.clientService = clientService;
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target;

        if (clientService.getClientByPassportData(client.getPassportData()).isPresent())
            errors.rejectValue("passportData", "", "Человек с таким номером паспорта уже существует");

        try {
            clientDetailsService.loadUserByUsername(client.getEmail());
        } catch (UsernameNotFoundException ignored) {
            return;
        }
        errors.rejectValue("email", "", "Человек с таким email уже существует");
    }
}
