package com.mpoznyak.projectsservice.repository;


import com.mpoznyak.projectsservice.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    List<User> findAllByNameContainsAndEmailContains(String name, String email, Pageable pageable);
}
