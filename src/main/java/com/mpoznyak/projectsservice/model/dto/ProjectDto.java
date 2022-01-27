package com.mpoznyak.projectsservice.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProjectDto {

    private Long id;
    @NotNull
    private String name;
    private String description;
    private List<UserDto> users;
}
