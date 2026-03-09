package com.info.task.service;

import com.info.task.entity.People;

import java.util.List;
import java.util.Optional;

public interface InfoRestService {

    People save(People people);

    People update(Long id, People people);

    List<People> searchByName(String name);

    List<People> searchByPin(String pin);

    List<People> findAll();

    Optional<People> findById(Long id);
}
