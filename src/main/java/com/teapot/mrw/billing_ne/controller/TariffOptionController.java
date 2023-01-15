package com.teapot.mrw.billing_ne.controller;

import com.teapot.mrw.billing_ne.model.TariffOption;
import com.teapot.mrw.billing_ne.service.TariffOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/tariffoptions")
public class TariffOptionController {

    TariffOptionService tariffOptionService;

    @Autowired
    public TariffOptionController(TariffOptionService tariffOptionService) {
        this.tariffOptionService = tariffOptionService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public String showTariffOptionList(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(value = "tariff_options_per_page", required = false, defaultValue = "10") Integer tariffOptionsPerPage) {

        Page<TariffOption> tariffOptionPage = tariffOptionService.findWithPagination(page, tariffOptionsPerPage);

        model.addAttribute("tariffOptionsListPage", tariffOptionPage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPage", tariffOptionPage.getTotalPages());
        model.addAttribute("paging", tariffOptionPage.getTotalElements() > tariffOptionsPerPage);
        return "tariffoptions/index";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("tariffOption", tariffOptionService.findTariffOption(id));
        return "tariffoptions/show";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String newTariffOption(@ModelAttribute("tariffOption") TariffOption tariffOption) {
        return "tariffoptions/new";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public String create(@ModelAttribute("tariffOption") @Valid TariffOption tariffOption,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "tariffoptions/new";

        tariffOptionService.save(tariffOption);
        return "redirect:/tariffoptions/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("tariffOption", tariffOptionService.findTariffOption(id));
        return "tariffoptions/edit";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("tariffOptions") @Valid TariffOption tariffOption, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "tariffoptions/edit";

        tariffOptionService.update(id, tariffOption);
        return "redirect:/tariffoptions/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        tariffOptionService.delete(id);
        return "redirect:/tariffoptions/";
    }
}
