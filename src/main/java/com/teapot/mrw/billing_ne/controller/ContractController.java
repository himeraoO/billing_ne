package com.teapot.mrw.billing_ne.controller;

import com.teapot.mrw.billing_ne.model.Contract;
import com.teapot.mrw.billing_ne.model.Tariff;
import com.teapot.mrw.billing_ne.model.TariffOption;
import com.teapot.mrw.billing_ne.service.ClientService;
import com.teapot.mrw.billing_ne.service.ContractService;
import com.teapot.mrw.billing_ne.service.TariffOptionService;
import com.teapot.mrw.billing_ne.service.TariffService;
import com.teapot.mrw.billing_ne.util.UtilForClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;
    private final TariffService tariffService;
    private final TariffOptionService tariffOptionService;
    private final ClientService clientService;
    private final UtilForClientDetails utilForClientDetails;

    @Autowired
    public ContractController(ContractService contractService, TariffService tariffService, TariffOptionService tariffOptionService, ClientService clientService, UtilForClientDetails utilForClientDetails, UtilForClientDetails utilForClientDetails1) {
        this.contractService = contractService;
        this.tariffService = tariffService;
        this.tariffOptionService = tariffOptionService;
        this.clientService = clientService;
        this.utilForClientDetails = utilForClientDetails1;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public String showContractList(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(value = "contracts_per_page", required = false, defaultValue = "10") Integer contractsPerPage) {

        Page<Contract> contractPage = contractService.findWithPagination(page, contractsPerPage);

        model.addAttribute("contractsListPage", contractPage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPage", contractPage.getTotalPages());
        model.addAttribute("paging", contractPage.getTotalElements() > contractsPerPage);
        return "contracts/index";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String newContract(@ModelAttribute("contract") Contract contract, Model model) {
        model.addAttribute("tariffList", tariffService.findAll());
        model.addAttribute("clientList", clientService.findAll());
        return "contracts/new";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public String create(@ModelAttribute("contract") @Valid Contract contract,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "contracts/new";

        contractService.save(contract);
        return "redirect:/contracts/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, @ModelAttribute("contractOption") TariffOption contractOption, Model model) {
        Contract contract = contractService.findContract(id);
        if (contract != null){
            List<TariffOption> listTariffOption = new ArrayList<>(contract.getTariff().getTariffOptionList());
            listTariffOption.removeAll(contract.getTariffOptionList());

            model.addAttribute("contract", contract);
            model.addAttribute("tariffOptions", listTariffOption);
        }
        return "contracts/show";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("contract", contractService.findContract(id));
        model.addAttribute("tariffList", tariffService.findAll());
        model.addAttribute("clientList", clientService.findAll());
        return "contracts/edit";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("contract") @Valid Contract contract,
                         BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "contracts/edit";

        contractService.update(id, contract);

        return "redirect:/contracts/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        contractService.delete(id);
        return "redirect:/contracts/";
    }

    @PatchMapping("/{id}/blocking")
    public String blocking(@PathVariable("id") long id){
        contractService.blocking(id);
        return "redirect:/contracts/"  + id;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/unlocking")
    public String unlocking(@PathVariable("id") long id){
        contractService.unlocking(id);
        return "redirect:/contracts/"  + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") long id, @ModelAttribute("contractOptionRemove") TariffOption selectedTariffOption) {
        contractService.releaseTariffOptions(id, tariffOptionService.findTariffOption(selectedTariffOption.getId()));
        return "redirect:/contracts/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") long id, @ModelAttribute("contractOption") TariffOption selectedTariffOption) {
        contractService.assignTariffOptions(id, selectedTariffOption);
        return "redirect:/contracts/" + id;
    }

    @GetMapping("/{id}/change-tariff")
    public String change(@ModelAttribute("tariff") Tariff tariff, Model model, @PathVariable("id") long id) {
        model.addAttribute("contract", contractService.findContract(id));

        if (utilForClientDetails.isRole("ROLE_ADMIN")) {
            model.addAttribute("tariffList", tariffService.findAll());
        }else {
            model.addAttribute("tariffList", tariffService.findAllByActiveTrue());
        }

        return "contracts/change_tariff";
    }

    @PatchMapping("/{id}/change-tariff")
    public String updateTariff(@ModelAttribute("tariff") Tariff tariff, @PathVariable("id") long id) {
        contractService.changeTariff(id, tariff);
        return "redirect:/contracts/" + id;
    }
}
