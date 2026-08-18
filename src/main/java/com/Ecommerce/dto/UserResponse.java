package com.Ecommerce.dto;

import com.Ecommerce.Model.UserRole;

import lombok.Data;

@Data
public class UserResponse {

  private String id;
  private String userName;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private UserRole role;
  private AddressDTO address;

}
