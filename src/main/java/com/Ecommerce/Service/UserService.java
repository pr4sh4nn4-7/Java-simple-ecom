package com.Ecommerce.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Ecommerce.Repository.UserRepo;
import com.Ecommerce.dto.AddressDTO;
import com.Ecommerce.dto.UserRequest;
import com.Ecommerce.dto.UserResponse;
import com.Ecommerce.Model.Address;
import com.Ecommerce.Model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepo userRepo;

  public List<UserResponse> fetchAllUsers() {

    return userRepo.findAll().stream().map(this::mapToUserResponse).collect(Collectors.toList());

  }

  public ResponseEntity<String> createUsers(UserRequest userRequest) {

    User user = new User();
    updateUserFromRequest(user, userRequest);
    userRepo.save(user);
    return new ResponseEntity<>("User created Successfully!", HttpStatus.CREATED);
  }

  public Optional<UserResponse> fetchSingleUser(Long id) {
    // for (User user : userList) {
    // if (user.getId().equals(id)) {
    // return user;
    // }
    // return null
    // }
    // //// alternative use

    // return userList.stream().filter(user ->
    // user.getId().equals(id)).findFirst();// get becuase we don't need to

    return userRepo.findById(id).map(this::mapToUserResponse);
  }

  public Boolean updateUser(Long id, UserRequest updatedUser) {
    return userRepo.findById(id).map(existinguser -> {
      updateUserFromRequest(existinguser, updatedUser);
      userRepo.save(existinguser);
      return true;
    }).orElse(false);
    // return userList.stream().filter(user ->
    // user.getId().equals(id)).findFirst().map(existinguser -> {
    // existinguser.setEmail(updatedUser.getEmail());
    // existinguser.setPhoneNumber(updatedUser.getPhoneNumber());
    // existinguser.setUserName(updatedUser.getUserName());
    // return true;
    // }).orElse(false);

    // alternative way
  }

  private void updateUserFromRequest(User user, UserRequest userRequest) {
    user.setFirstName(userRequest.getFirstName());
    user.setLastName(userRequest.getLastName());
    user.setUserName(userRequest.getUserName());
    user.setPhoneNumber(userRequest.getPhoneNumber());
    user.setEmail(userRequest.getEmail());

    if (userRequest.getAddress() != null) {
      Address address = new Address();
      address.setStreet(userRequest.getAddress().getStreet());
      address.setCity(userRequest.getAddress().getCity());
      address.setState(userRequest.getAddress().getState());
      address.setCountry(userRequest.getAddress().getCountry());
      address.setZipcode(userRequest.getAddress().getZipcode());
      user.setAddress(address);

    }

  }

  private UserResponse mapToUserResponse(User user) {
    UserResponse response = new UserResponse();
    response.setEmail(user.getEmail());
    response.setFirstName(user.getFirstName());
    response.setPhoneNumber(user.getPhoneNumber());
    response.setLastName(user.getLastName());
    response.setUserName(user.getUserName());
    response.setRole(user.getRole());
    response.setId(user.getId().toString());

    if (user.getAddress() != null) {
      AddressDTO addressDTO = new AddressDTO();
      addressDTO.setStreet(user.getAddress().getStreet());
      addressDTO.setCity(user.getAddress().getCity());
      addressDTO.setCountry(user.getAddress().getCountry());
      addressDTO.setState(user.getAddress().getStreet());
      addressDTO.setZipcode(user.getAddress().getZipcode());
      response.setAddress(addressDTO);
    }

    return response;

  }
}
