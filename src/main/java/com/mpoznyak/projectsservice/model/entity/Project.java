package com.mpoznyak.projectsservice.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "project")
@Getter
@Setter
public class Project {

    @Id
    private Long id;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "users_project",
            joinColumns = { @JoinColumn(name = "project_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<User> users;

    @Column
    private String name;

    @Column
    private String description;

}
