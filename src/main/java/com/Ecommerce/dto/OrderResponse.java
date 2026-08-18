package com.Ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.Ecommerce.Model.OrderItem;
import com.Ecommerce.Model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
  private Long id;
  private BigDecimal totalAmount;
  private OrderStatus status;
  private List<OrderItemDTO> items;
  private LocalDateTime createdAt;

}
