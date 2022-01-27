package com.mpoznyak.projectsservice.service;

import com.mpoznyak.projectsservice.model.dto.ProjectDto;
import com.mpoznyak.projectsservice.model.dto.UserDto;

import java.util.List;

public interface ProjectService {
    ProjectDto getById(Long id);

    List<ProjectDto> getAll();
    List<ProjectDto> getAllPaginated(Integer page, Integer size);

    void delete(Long id);

    ProjectDto create(ProjectDto project);

    ProjectDto update(ProjectDto project);

    ProjectDto assignNewUser(Long id, UserDto user);

    List<ProjectDto> findByName(String name, Integer page, Integer size);

    void assign(Long projectId, Long userId);
}
