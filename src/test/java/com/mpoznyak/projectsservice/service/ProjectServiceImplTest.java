package com.mpoznyak.projectsservice.service;

import com.mpoznyak.projectsservice.exception.UserAlreadyAssignedException;
import com.mpoznyak.projectsservice.mapper.ProjectMapper;
import com.mpoznyak.projectsservice.mapper.UserMapper;
import com.mpoznyak.projectsservice.model.dto.ProjectDto;
import com.mpoznyak.projectsservice.model.dto.UserDto;
import com.mpoznyak.projectsservice.model.entity.Project;
import com.mpoznyak.projectsservice.model.entity.User;
import com.mpoznyak.projectsservice.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMapper projectMapper;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private ProjectServiceImpl projectService;

    private User user;
    private Project project;
    private ProjectDto projectDto;
    private UserDto userDto;
    private final String email = "email@email.com";
    private final Long userId = 1L;
    private final Long projectId = 1L;
    private final String name = "testName";
    private final String description = "testDescription";
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(userId);
        user.setEmail(email);
        user.setName(name);

        userDto = new UserDto();
        userDto.setId(userId);
        userDto.setEmail(email);
        userDto.setName(name);

        projectDto = new ProjectDto();
        projectDto.setId(1L);
        projectDto.setName(name);
        projectDto.setDescription(description);
        projectDto.setUsers(List.of(new UserDto()));

        project = new Project();
        project.setId(projectId);
        project.setName(name);
        project.setDescription(description);
    }

    @Test
    void testGetById() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        projectService.getById(userId);
        verify(projectMapper).entityToDto(project);
        verify(projectRepository).findById(userId);
    }

    @Test
    void getAll() {
        project.setUsers(List.of(user));

        when(projectRepository.findAll()).thenReturn(List.of(project));
        projectService.getAll();
        verify(projectMapper).entityToDto(anyList());
        verify(projectRepository).findAll();

        Assertions.assertNull(project.getUsers());
    }

    @Test
    void getAllPaginated() {
        PageRequest pageRequest = PageRequest.of(1,1);
        when(projectRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(List.of(project)));
        projectService.getAllPaginated(1,1);
        verify(projectMapper).entityToDto(anyList());
        verify(projectRepository).findAll(pageRequest);
    }

    @Test
    void delete() {
        doNothing().when(projectRepository).deleteById(projectId);
        projectService.delete(projectId);
        verify(projectRepository).deleteById(projectId);
    }

    @Test
    void create() {
        when(projectRepository.save(any())).thenReturn(project);
        when(projectMapper.entityToDto(project)).thenReturn(projectDto);

        projectService.create(projectDto);

        verify(projectRepository).save(any());
        verify(projectMapper).entityToDto(project);
        verify(projectMapper).dtoToEntity(projectDto);
    }

    @Test
    void update() {
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        projectService.update(projectDto);

        verify(projectRepository).findById(projectId);
        verify(projectRepository).save(project);
    }

    @Test
    void updateForNonExistentEntity() {
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> projectService.update(projectDto));
    }
    @Test
    void assignNewUser() {
        user.setId(3L);
        List<User> list = new ArrayList<>();
        list.add(user);
        project.setUsers(list);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(userMapper.dtoToEntity(userDto)).thenReturn(user);
        projectService.assignNewUser(projectId, userDto);
        verify(projectRepository).save(project);
    }
    @Test
    void assignNewUserUserAlreadyAssigned() {
        project.setUsers(List.of(user));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Assertions.assertThrows(UserAlreadyAssignedException.class, () -> projectService.assignNewUser(projectId, userDto));
    }

    @Test
    void findByName() {
        when(projectRepository.findAllByNameContains(name, PageRequest.of(1,1))).thenReturn(List.of(project));
        projectService.findByName(name, 1,1);
        verify(projectRepository).findAllByNameContains(name, PageRequest.of(1,1));
        verify(projectMapper).entityToDto(anyList());
    }

    @Test
    void assign() {
        user.setId(3L);
        List<User> list = new ArrayList<>();
        list.add(user);
        project.setUsers(list);

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        doNothing().when(projectRepository).createRelation(anyLong(), anyLong());
        projectService.assign(1L, 2L);
        verify(projectRepository).createRelation(1L, 2L);
    }
}