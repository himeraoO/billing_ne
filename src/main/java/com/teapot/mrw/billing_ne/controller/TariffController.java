package com.teapot.mrw.billing_ne.controller;

import com.teapot.mrw.billing_ne.model.Tariff;
import com.teapot.mrw.billing_ne.model.TariffOption;
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
@RequestMapping("/tariffs")
public class TariffController {

    private final TariffService tariffService;
    private final TariffOptionService tariffOptionService;
    private final UtilForClientDetails utilForClientDetails;

    @Autowired
    public TariffController(TariffService tariffService, TariffOptionService tariffOptionService, UtilForClientDetails utilForClientDetails, UtilForClientDetails utilForClientDetails1) {
        this.tariffService = tariffService;
        this.tariffOptionService = tariffOptionService;
        this.utilForClientDetails = utilForClientDetails1;
    }

    @GetMapping("/")
    public String showTariffList(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(value = "tariffs_per_page", required = false, defaultValue = "10") Integer tariffsPerPage) {

        Page<Tariff> tariffPage;

        if (utilForClientDetails.isRole("ROLE_ADMIN")) {
            tariffPage = tariffService.findWithPagination(page, tariffsPerPage);
        }else {
            tariffPage = tariffService.findWithPaginationByActiveTrue(page, tariffsPerPage);
        }

        model.addAttribute("tariffsListPage", tariffPage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPage", tariffPage.getTotalPages());
        model.addAttribute("paging", tariffPage.getTotalElements() > tariffsPerPage);

        return "tariffs/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, @ModelAttribute("tariffOption") TariffOption tariffOption, Model model) {
        List<TariffOption> listTariffOption = new ArrayList<>(tariffOptionService.findAll());
        Tariff tariff = tariffService.findTariff(id);
        listTariffOption.removeAll(tariff.getTariffOptionList());
        model.addAttribute("tariff", tariff);
        model.addAttribute("tariffOptions", listTariffOption);
        return "tariffs/show";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String newTariff(@ModelAttribute("tariff") Tariff tariff) {
        return "tariffs/new";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public String create(@ModelAttribute("tariff") @Valid Tariff tariff,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "tariffs/new";

        tariffService.save(tariff);
        return "redirect:/tariffs/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("tariff", tariffService.findTariff(id));
        return "tariffs/edit";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("tariff") @Valid Tariff tariff, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "tariffs/edit";

        tariffService.update(id, tariff);
        return "redirect:/tariffs/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        tariffService.delete(id);
        return "redirect:/tariffs/";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") long id, @ModelAttribute("tariffOptionRemove") TariffOption selectedTariffOption) {
        tariffService.release(id, tariffOptionService.findTariffOption(selectedTariffOption.getId()));
        return "redirect:/tariffs/" + id;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") long id, @ModelAttribute("tariffOption") TariffOption selectedTariffOption) {
        tariffService.assign(id, selectedTariffOption);
        return "redirect:/tariffs/" + id;
    }
}
