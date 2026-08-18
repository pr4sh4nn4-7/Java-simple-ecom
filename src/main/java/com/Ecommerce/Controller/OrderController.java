package com.Ecommerce.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ecommerce.Service.OrderService;
import com.Ecommerce.dto.OrderRequest;
import com.Ecommerce.dto.OrderResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-USER-ID") String userId) {
    // OrderResponse order = orderService.createOrder(userId);
    // return ResponseEntity.ok(Map.of(
    // "success", true,
    // "message", "order placed succssfully",
    // "data", order));
    return orderService.createOrder(userId).map(orderResponse -> new ResponseEntity<>(orderResponse, HttpStatus.OK))
        .orElseGet(() -> ResponseEntity.badRequest().build());

  }

}
