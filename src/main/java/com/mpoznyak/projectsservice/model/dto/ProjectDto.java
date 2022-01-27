package com.mpoznyak.projectsservice.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {

    private Long id;
    private String name;
    private String description;
    private List<UserDto> users;
}
