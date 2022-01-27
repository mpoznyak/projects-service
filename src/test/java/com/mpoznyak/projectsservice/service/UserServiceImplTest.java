package com.mpoznyak.projectsservice.service;

import com.mpoznyak.projectsservice.mapper.UserMapper;
import com.mpoznyak.projectsservice.model.dto.ProjectDto;
import com.mpoznyak.projectsservice.model.dto.UserDto;
import com.mpoznyak.projectsservice.model.entity.Project;
import com.mpoznyak.projectsservice.model.entity.User;
import com.mpoznyak.projectsservice.repository.UserRepository;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;
    private User user;
    private UserDto userDto;
    private final String email = "email@email.com";
    private final Long userId = 1L;
    private final String name = "testName";

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
    }

    @Test
    void getById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        userService.getById(userId);
        verify(userMapper).entityToDto(user);
        verify(userRepository).findById(userId);
    }

    @Test
    void getAll() {

        when(userRepository.findAll()).thenReturn(List.of(user));
        userService.getAll();
        verify(userMapper).entityToDto(anyList());
        verify(userRepository).findAll();
    }

    @Test
    void delete() {
        doNothing().when(userRepository).deleteById(userId);
        userService.delete(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void create() {
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.entityToDto(user)).thenReturn(userDto);

        userService.create(userDto);

        verify(userRepository).save(any());
        verify(userMapper).entityToDto(user);
        verify(userMapper).dtoToEntity(userDto);
    }

    @Test
    void update() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.update(userDto);

        verify(userRepository).findById(userId);
        verify(userRepository).save(user);
    }
    @Test
    void updateForNonExistentEntity() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.update(userDto));
    }

    @Test
    void findByNameAndEmail() {
        when(userRepository.findAllByNameContainsAndEmailContains(name, email, PageRequest.of(1, 1)))
                .thenReturn(List.of(user));
        userService.findByNameAndEmail(name, email, 1, 1);
        verify(userRepository).findAllByNameContainsAndEmailContains(name, email, PageRequest.of(1, 1));
        verify(userMapper).entityToDto(anyList());
    }

    @Test
    void getAllPaginated() {
        PageRequest pageRequest = PageRequest.of(1,1);
        when(userRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(List.of(user)));
        userService.getAllPaginated(1,1);
        verify(userMapper).entityToDto(anyList());
        verify(userRepository).findAll(pageRequest);
    }
}