package com.Ecommerce.dto;

import lombok.Data;

@Data
public class CartRequest {

  private Long productId;
  // private User user;
  // private Product product;
  private Integer quantity;
  // private BigDecimal price;

}
