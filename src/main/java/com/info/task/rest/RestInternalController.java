package com.info.task.rest;

import com.info.task.entity.People;
import com.info.task.service.impl.InfoRestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class RestInternalController {

    private static final Logger LOG = LoggerFactory.getLogger(RestInternalController.class);

    private final InfoRestServiceImpl signupRestService;

    @Autowired
    public RestInternalController(InfoRestServiceImpl signupRestService){
        this.signupRestService = signupRestService;
    }

    // Add person
    @PostMapping
    public People create(@RequestBody People people) {
        return signupRestService.save(people);
    }

    // Update person
    @PutMapping("/{id}")
    public People update(@PathVariable Long id,
                         @RequestBody People people) {
        return signupRestService.update(id, people);
    }

    // Search by name
    @GetMapping("/search/name")
    public List<People> searchByName(@RequestParam String name) {
        return signupRestService.searchByName(name);
    }

    // Search by pin
    @GetMapping("/search/pin")
    public List<People> searchByPin(@RequestParam String pin) {
        return signupRestService.searchByPin(pin);
    }

    // Get all
    @GetMapping
    public List<People> findAll() {
        return signupRestService.findAll();
    }
}
