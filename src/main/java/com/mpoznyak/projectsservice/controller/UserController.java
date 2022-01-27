package com.mpoznyak.projectsservice.controller;

import com.mpoznyak.projectsservice.model.dto.UserDto;
import com.mpoznyak.projectsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> getAllPaginated(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return ResponseEntity.ok(userService.getAllPaginated(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> findByNameAndEmail(@RequestParam("name") String name,
                                                            @RequestParam("email") String email,
                                                            @RequestParam("page") Integer page,
                                                            @RequestParam("size") Integer size) {
        return ResponseEntity.ok(userService.findByNameAndEmail(name, email, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }
    @PutMapping("")
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.update(userDto));
    }
    @PostMapping("")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.create(userDto));
    }
}
