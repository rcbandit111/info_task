package com.info.task.rest;

import com.info.task.entity.People;
import com.info.task.service.InfoRestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeoplePageController {

    private final InfoRestService peopleService;

    @Autowired
    public PeoplePageController(InfoRestService peopleService) {
        this.peopleService = peopleService;
    }

    // LIST all people
    @GetMapping
    public String listPeople(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/list";
    }

    // SHOW create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("people", new People());
        return "people/form";
    }

    // CREATE a new person
    @PostMapping
    public String createPerson(@Valid @ModelAttribute("people") People people,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "people/form";
        }
        peopleService.save(people);
        redirectAttributes.addFlashAttribute("message", "Person created successfully");
        return "redirect:/people";
    }

    // SHOW edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        People people = peopleService.findById(id)   // we'll add this method
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));
        model.addAttribute("people", people);
        return "people/form";
    }

    // UPDATE existing person
    @PostMapping("/{id}")
    public String updatePerson(@PathVariable Long id,
                               @Valid @ModelAttribute("people") People people,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "people/form";
        }
        peopleService.update(id, people);
        redirectAttributes.addFlashAttribute("message", "Person updated successfully");
        return "redirect:/people";
    }

    // SEARCH by name or PIN
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

    // DELETE person
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

}