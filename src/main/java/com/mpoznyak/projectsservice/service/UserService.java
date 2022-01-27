package com.mpoznyak.projectsservice.service;

import com.mpoznyak.projectsservice.model.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getById(Long id);

    List<UserDto> getAll();

    void delete(Long id);

    UserDto create(UserDto project);

    UserDto update(UserDto project);

    List<UserDto> findByNameAndEmail(String name, String email, Integer page, Integer size);

    List<UserDto> getAllPaginated(Integer page, Integer size);
}
