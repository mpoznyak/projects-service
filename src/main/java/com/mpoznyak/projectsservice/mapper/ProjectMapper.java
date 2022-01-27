package com.mpoznyak.projectsservice.mapper;

import com.mpoznyak.projectsservice.model.dto.ProjectDto;
import com.mpoznyak.projectsservice.model.entity.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectDto entityToDto(Project project);
    Project dtoToEntity(ProjectDto project);
    List<ProjectDto> entityToDto(List<Project> projects);
}