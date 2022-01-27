package com.mpoznyak.projectsservice.controller;

import com.mpoznyak.projectsservice.model.dto.ProjectDto;
import com.mpoznyak.projectsservice.model.dto.UserDto;
import com.mpoznyak.projectsservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/list")
    public ResponseEntity<List<ProjectDto>> getAllPaginated(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return ResponseEntity.ok(projectService.getAllPaginated(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProjectDto>> findByName(@RequestParam("name") String name,
                                                       @RequestParam("page") Integer page,
                                                       @RequestParam("size") Integer size) {
        return ResponseEntity.ok(projectService.findByName(name, page, size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(projectService.getById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<ProjectDto>> getAll() {
        return ResponseEntity.ok(projectService.getAll());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        projectService.delete(id);
    }

    @PutMapping("")
    public ResponseEntity<ProjectDto> update(@RequestBody ProjectDto project) {
        return ResponseEntity.ok(projectService.update(project));
    }

    @PostMapping("")
    public ResponseEntity<ProjectDto> create(@RequestBody ProjectDto project) {
        return ResponseEntity.ok(projectService.create(project));
    }

    @PutMapping("/{project-id}/user/new")
    public ResponseEntity<ProjectDto> assignNewUser(@PathVariable("project-id") Long id, @RequestBody UserDto user) {
        return ResponseEntity.ok(projectService.assignNewUser(id, user));
    }
    @PutMapping("/{project-id}/user/{id}")
    public ResponseEntity assign(@PathVariable("project-id") Long projectId, @PathVariable("id") Long userId) {
        projectService.assign(projectId, userId);
        return ResponseEntity.ok().build();
    }
}
