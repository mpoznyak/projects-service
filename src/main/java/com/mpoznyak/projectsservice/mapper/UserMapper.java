package com.mpoznyak.projectsservice.mapper;

import com.mpoznyak.projectsservice.model.dto.UserDto;
import com.mpoznyak.projectsservice.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto entityToDto(User user);
    User dtoToEntity(UserDto user);
    List<UserDto> entityToDto(List<User> users);
    List<User> dtoToEntity(List<UserDto> users);
}