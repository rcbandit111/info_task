package com.info.task.service.impl;

import com.info.task.entity.People;
import com.info.task.entity.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InfoRestServiceImpl {

    private final PeopleRepository peopleRepository;

    @Autowired
    public InfoRestServiceImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<People> findAll() {
        return peopleRepository.findAll();
    }

    public Optional<People> findById(Long id) {
        return peopleRepository.findById(id);
    }

    // Fetch person with addresses and mails (eagerly)
    @Transactional
    public Optional<People> findByIdWithDetails(Long id) {
        Optional<People> people = peopleRepository.findById(id);
        people.ifPresent(p -> {
            // Force initialization of lazy collections
            p.getAddresses().size();
            p.getMails().size();
        });
        return people;
    }

    @Transactional
    public People save(People people) {
        return peopleRepository.save(people);
    }

    @Transactional
    public People update(Long id, People updated) {
        return peopleRepository.findById(id)
                .map(existing -> {
                    existing.setFullName(updated.getFullName());
                    existing.setPin(updated.getPin());

                    // Replace addresses
                    existing.getAddresses().clear();
                    if (updated.getAddresses() != null) {
                        updated.getAddresses().forEach(addr -> {
                            addr.setPeople(existing);
                            existing.getAddresses().add(addr);
                        });
                    }

                    // Replace emails
                    existing.getMails().clear();
                    if (updated.getMails() != null) {
                        updated.getMails().forEach(mail -> {
                            mail.setPeople(existing);
                            existing.getMails().add(mail);
                        });
                    }

                    return peopleRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
    }

    @Transactional
    public void deleteById(Long id) {
        peopleRepository.deleteById(id);
    }

    public List<People> searchByName(String name) {
        return peopleRepository.findByFullNameContainingIgnoreCase(name);
    }

    public List<People> searchByPin(String pin) {
        return peopleRepository.findByPin(pin);
    }
}