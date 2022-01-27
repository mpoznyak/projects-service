package com.mpoznyak.projectsservice.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @Email
    private String email;
}
