package com.Ecommerce.dto;

import com.Ecommerce.Model.UserRole;

import lombok.Data;

@Data
public class UserRequest {

  private String userName;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private AddressDTO address;

}
