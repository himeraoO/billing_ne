package com.teapot.mrw.billing_ne.controller;

import com.teapot.mrw.billing_ne.dto.PasswordDTO;
import com.teapot.mrw.billing_ne.service.ClientService;
import com.teapot.mrw.billing_ne.service.PasswordService;
import com.teapot.mrw.billing_ne.util.UtilForClientDetails;
import com.teapot.mrw.billing_ne.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/lk")
public class PersonalAccountController {

    private final ClientService clientService;
    private final PasswordService passwordService;
    private final PasswordValidator passwordValidator;
    private final UtilForClientDetails utilForClientDetails;

    @Autowired
    public PersonalAccountController(ClientService clientService, PasswordService passwordService, PasswordValidator passwordValidator, UtilForClientDetails utilForClientDetails) {
        this.clientService = clientService;
        this.passwordService = passwordService;
        this.passwordValidator = passwordValidator;
        this.utilForClientDetails = utilForClientDetails;
    }

    @GetMapping()
    public String showMain(Model model) {
        model.addAttribute("client", clientService.findClient(utilForClientDetails.getClientIdFromClientDetails()));
        return "lk/personal_account";
    }

    @GetMapping("/change_password")
    public String changePassword(@ModelAttribute("passwordDTO") PasswordDTO passwordDTO) {
        return "lk/change_password";
    }

    @PatchMapping("/change_password")
    public String updatePassword(@ModelAttribute("passwordDTO") @Valid PasswordDTO passwordDTO,
                         BindingResult bindingResult) {

        passwordValidator.validate(passwordDTO, bindingResult);

        if (bindingResult.hasErrors())
            return "lk/change_password";

        passwordService.updatePassword(utilForClientDetails.getClientIdFromClientDetails(), passwordDTO);

        return "redirect:/lk/";
    }

}
