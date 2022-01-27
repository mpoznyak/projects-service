package com.mpoznyak.projectsservice.service;

import com.mpoznyak.projectsservice.mapper.UserMapper;
import com.mpoznyak.projectsservice.model.dto.UserDto;
import com.mpoznyak.projectsservice.model.entity.User;
import com.mpoznyak.projectsservice.repository.UserRepository;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UserDto getById(Long id) {
        return userMapper.entityToDto(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<UserDto> getAll() {
        return userMapper.entityToDto(Streamable.of(userRepository.findAll()).toList());
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = userRepository.save(userMapper.dtoToEntity(userDto));
        return userMapper.entityToDto(user);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(EntityNotFoundException::new);
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user = userRepository.save(user);
        return userMapper.entityToDto(user);
    }

    @Override
    public List<UserDto> findByNameAndEmail(String name, String email, Integer page, Integer size) {
        return userMapper.entityToDto(userRepository
                .findAllByNameContainsAndEmailContains(name, email, PageRequest.of(page, size)));
    }

    @Override
    public List<UserDto> getAllPaginated(Integer page, Integer size) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        return userMapper.entityToDto(users.stream().collect(Collectors.toList()));
    }
}
