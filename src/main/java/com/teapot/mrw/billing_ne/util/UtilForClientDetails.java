package com.teapot.mrw.billing_ne.util;

import com.teapot.mrw.billing_ne.model.Client;
import com.teapot.mrw.billing_ne.security.ClientDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UtilForClientDetails {
    public ClientDetails getClientDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (ClientDetails) authentication.getPrincipal();
    }

    public Long getClientIdFromClientDetails(){
        Client client = getClientFromClientDetails();
        return client.getId();
    }

    public Client getClientFromClientDetails(){
        ClientDetails clientDetails = getClientDetails();
        return clientDetails.getClient();
    }

    public boolean isRole(String role){
        return getClientFromClientDetails().getRole().equals(role);
    }

}
