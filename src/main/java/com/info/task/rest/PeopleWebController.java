package com.info.task.rest;

import com.info.task.entity.Addresses;
import com.info.task.entity.Mails;
import com.info.task.entity.People;
import com.info.task.service.impl.InfoRestServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleWebController {

    private final InfoRestServiceImpl peopleService;

    @Autowired
    public PeopleWebController(InfoRestServiceImpl peopleService) {
        this.peopleService = peopleService;
    }

    // List all people
    @GetMapping
    public String listPeople(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/list";
    }

    // Search by name or PIN
    @GetMapping("/search")
    public String search(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String pin,
                         Model model) {
        List<People> results;
        if (name != null && !name.isEmpty()) {
            results = peopleService.searchByName(name);
        } else if (pin != null && !pin.isEmpty()) {
            results = peopleService.searchByPin(pin);
        } else {
            results = peopleService.findAll();
        }
        model.addAttribute("people", results);
        model.addAttribute("searchName", name);
        model.addAttribute("searchPin", pin);
        return "people/list";
    }

    // Show create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        People people = new People();
        people.setAddresses(new ArrayList<>());
        people.setMails(new ArrayList<>());
        // Add one empty row for convenience
        people.getAddresses().add(new Addresses());
        people.getMails().add(new Mails());
        model.addAttribute("people", people);
        return "people/form";
    }

    // Create a new person (POST)
    @PostMapping
    public String createPerson(@Valid @ModelAttribute("people") People people,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "people/form";
        }
        setParentOnChildren(people);
        peopleService.save(people);
        redirectAttributes.addFlashAttribute("message", "Person created successfully");
        return "redirect:/people";
    }

    // Show edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        People people = peopleService.findByIdWithDetails(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));
        model.addAttribute("people", people);
        return "people/form";
    }

    // Update person (POST to /people/{id})
    @PostMapping("/{id}")
    public String updatePerson(@PathVariable Long id,
                               @Valid @ModelAttribute("people") People people,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "people/form";
        }
        people.setId(id);
        setParentOnChildren(people);
        peopleService.update(id, people);
        redirectAttributes.addFlashAttribute("message", "Person updated successfully");
        return "redirect:/people";
    }

    // Delete person
    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        try {
            peopleService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Person deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting person: " + e.getMessage());
        }
        return "redirect:/people";
    }

    // Helper to set parent reference on children
    private void setParentOnChildren(People people) {
        if (people.getAddresses() != null) {
            people.getAddresses().forEach(addr -> addr.setPeople(people));
        }
        if (people.getMails() != null) {
            people.getMails().forEach(mail -> mail.setPeople(people));
        }
    }
}