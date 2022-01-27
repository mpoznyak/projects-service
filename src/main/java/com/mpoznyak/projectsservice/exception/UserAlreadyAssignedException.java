package com.mpoznyak.projectsservice.exception;

public class UserAlreadyAssignedException extends RuntimeException {
    private final Long id;
    public UserAlreadyAssignedException(Long id) {
        super();
        this.id = id;
    }

    public Long getUserId() {
        return id;
    }
}
