package com.example.paynest.controller;

import com.example.paynest.model.User;
import com.example.paynest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //    CRUD : create
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody User user) {
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //    CRUD : read
    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    //    CRUD : update
    @PutMapping("/{id}")
    public ResponseEntity<User> updateById(@PathVariable Integer id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateById(id, user));
    }

    //    CRUD : delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
    }
}
