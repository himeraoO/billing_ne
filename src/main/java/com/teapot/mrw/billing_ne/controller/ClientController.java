package com.teapot.mrw.billing_ne.controller;

import com.teapot.mrw.billing_ne.dto.ClientDTO;
import com.teapot.mrw.billing_ne.dto.SearchDTO;
import com.teapot.mrw.billing_ne.model.Client;
import com.teapot.mrw.billing_ne.service.ClientService;
import com.teapot.mrw.billing_ne.util.UtilForClientDetails;
import com.teapot.mrw.billing_ne.validator.ClientValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientValidator clientValidator;
    private final ModelMapper modelMapper;
    private final UtilForClientDetails utilForClientDetails;

    @Autowired
    public ClientController(ClientService clientService, ClientValidator clientValidator, ModelMapper modelMapper, UtilForClientDetails utilForClientDetails) {
        this.clientService = clientService;
        this.clientValidator = clientValidator;
        this.modelMapper = modelMapper;
        this.utilForClientDetails = utilForClientDetails;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public String showClientList(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                        @RequestParam(value = "clients_per_page", required = false, defaultValue = "10") Integer clientsPerPage) {

        Page<Client> clientPage = clientService.findWithPagination(page, clientsPerPage);

        model.addAttribute("clientsListPage", clientPage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPage", clientPage.getTotalPages());
        model.addAttribute("paging", clientPage.getTotalElements() > clientsPerPage);
        return "clients/index";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("client", clientService.findClient(id));
        model.addAttribute("currentAdmin", utilForClientDetails.getClientIdFromClientDetails().equals(id));
        return "clients/show";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") Client client) {
        return "clients/new";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public String create(@ModelAttribute("client") @Valid Client client,
                         BindingResult bindingResult) {

        clientValidator.validate(client, bindingResult);

        if (bindingResult.hasErrors())
            return "clients/new";

        clientService.save(client);
        return "redirect:/clients/";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("client", convertToClientDTO(clientService.findClient(id)));
        return "clients/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("client") @Valid ClientDTO clientDTO, BindingResult bindingResult,
                         @PathVariable("id") long id) {

        if (bindingResult.hasErrors())
            return "clients/edit";

        clientService.update(id, convertToClient(clientDTO));

        if(utilForClientDetails.isRole("ROLE_USER")){
            return "redirect:/lk/";
        }

        return "redirect:/clients/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        clientService.delete(id);
        return "redirect:/clients/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/search")
    public String clientsSearch(@ModelAttribute("query") SearchDTO searchDTO) {
        return "clients/search";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/search")
    public String makeClientsSearch(Model model, @ModelAttribute("query") @Valid SearchDTO searchDTO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "clients/search";

        model.addAttribute("clients", clientService.clientSearch(searchDTO));

        return "clients/search";
    }

    private Client convertToClient(ClientDTO clientDTO) {
        return this.modelMapper.map(clientDTO, Client.class);
    }

    private ClientDTO convertToClientDTO(Client client) {
        return this.modelMapper.map(client, ClientDTO.class);
    }
}
