package com.teapot.mrw.billing_ne.controller;

import com.teapot.mrw.billing_ne.model.Client;
import com.teapot.mrw.billing_ne.service.RegistrationService;
import com.teapot.mrw.billing_ne.validator.ClientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final ClientValidator clientValidator;

    @Autowired
    public AuthController(RegistrationService registrationService, ClientValidator clientValidator) {
        this.registrationService = registrationService;
        this.clientValidator = clientValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("client") Client client) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("client") @Valid Client client,
                                      BindingResult bindingResult) {

        clientValidator.validate(client, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }

        registrationService.register(client);

        return "redirect:/auth/login";
    }
}
