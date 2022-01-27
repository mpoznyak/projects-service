package com.mpoznyak.projectsservice.repository;

import com.mpoznyak.projectsservice.model.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
    List<Project> findAllByNameContains(String name, Pageable pageable);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO PUBLIC.users_project (project_id, user_id) values (?1, ?2)")
    void createRelation(Long projectId, Long userId);
}
