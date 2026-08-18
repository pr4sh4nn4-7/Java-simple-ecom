package com.Ecommerce.Controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ecommerce.Model.*;
import com.Ecommerce.Service.UserService;
import com.Ecommerce.dto.UserRequest;
import com.Ecommerce.dto.UserResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping
  // @RequestMapping(value = "/api/users",method = RequestMethod.GET)
  public List<UserResponse> getAllUsers() {
    return userService.fetchAllUsers();
  }

  @PostMapping
  public ResponseEntity<String> createUser(@RequestBody UserRequest user) {
    userService.createUsers(user);
    return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest updatedData) {
    Boolean updated = userService.updateUser(id, updatedData);
    if (updated)
      return ResponseEntity.ok("Data updated successfully");
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> fetchSingleUser(@PathVariable Long id) {

    return userService.fetchSingleUser(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

  }

}
