package com.mpoznyak.projectsservice.service;

import com.mpoznyak.projectsservice.exception.UserAlreadyAssignedException;
import com.mpoznyak.projectsservice.mapper.ProjectMapper;
import com.mpoznyak.projectsservice.mapper.UserMapper;
import com.mpoznyak.projectsservice.model.dto.ProjectDto;
import com.mpoznyak.projectsservice.model.dto.UserDto;
import com.mpoznyak.projectsservice.model.entity.Project;
import com.mpoznyak.projectsservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;

    @Override
    public ProjectDto getById(Long id) {
        return projectMapper.entityToDto(projectRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<ProjectDto> getAll() {
        List<Project> projects = Streamable.of(projectRepository.findAll()).toList();
        projects.forEach(x -> x.setUsers(null));
        return projectMapper.entityToDto(projects);
    }

    @Override
    public List<ProjectDto> getAllPaginated(Integer page, Integer size) {
        Page<Project> projects = projectRepository.findAll(PageRequest.of(page, size));
        return projectMapper.entityToDto(projects.stream().collect(Collectors.toList()));
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectDto create(ProjectDto userDto) {
        Project project = projectRepository.save(projectMapper.dtoToEntity(userDto));
        return projectMapper.entityToDto(project);
    }

    @Override
    public ProjectDto update(ProjectDto projectDto) {
        Project project = projectRepository.findById(projectDto.getId()).orElseThrow(EntityNotFoundException::new);
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setUsers(userMapper.dtoToEntity(projectDto.getUsers()));
        project = projectRepository.save(project);
        return projectMapper.entityToDto(project);
    }

    @Override
    public ProjectDto assignNewUser(Long id, UserDto user) {
        Project project = projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (project.getUsers().stream().anyMatch(x -> x.getId().equals(user.getId()))) {
            throw new UserAlreadyAssignedException(user.getId());
        }
        project.getUsers().add(userMapper.dtoToEntity(user));
        project = projectRepository.save(project);
        return projectMapper.entityToDto(project);
    }

    @Override
    public List<ProjectDto> findByName(String name, Integer page, Integer size) {
        return projectMapper.entityToDto(projectRepository.findAllByNameContains(name, PageRequest.of(page, size)));
    }

    @Override
    public void assign(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow(EntityNotFoundException::new);
        if (project.getUsers().stream().anyMatch(x -> x.getId().equals(userId))) {
            throw new UserAlreadyAssignedException(userId);
        }
        projectRepository.createRelation(projectId ,userId);
    }
}
