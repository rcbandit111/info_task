package com.info.task.service.impl;

import com.info.task.entity.People;
import com.info.task.entity.PeopleRepository;
import com.info.task.service.InfoRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InfoRestServiceImpl implements InfoRestService {

    private PeopleRepository peopleRepository;

    @Autowired
    public InfoRestServiceImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public People save(People people) {
        return peopleRepository.save(people);
    }

    @Override
    public People update(Long id, People people) {
        return peopleRepository.save(people);
    }

    @Override
    public List<People> searchByName(String name) {
        return List.of();
    }

    @Override
    public List<People> searchByPin(String pin) {
        return List.of();
    }

    @Override
    public List<People> findAll() {
        return peopleRepository.findAll();
    }

    @Override
    public Optional<People> findById(Long id) {
        return peopleRepository.findById(id);
    }
}
