package com.Ecommerce.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ecommerce.Model.Cart;
import com.Ecommerce.Service.CartService;
import com.Ecommerce.dto.CartRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")

@RequiredArgsConstructor
public class CartController {
  private final CartService cartService;

  @PostMapping("/create")
  public ResponseEntity<Map> addToCart(@RequestHeader(value = "X-USER-ID", required = false) String userId,
      @RequestBody CartRequest requestData) {
    if (userId == null || userId.isBlank() || userId.isEmpty()) {
      return ResponseEntity.badRequest().body(Map.of("status", 400,
          "message", "user not found"));

    }
    if (cartService.addToCart(userId, requestData)) {
      return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
          "status", 201,
          "message", "Added successfully"));
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of(
            "status", "400",
            "message", "Something went wrong"));

  }

  @DeleteMapping("/remove/{productId}")
  public ResponseEntity<Map> removeFromCart(
      @RequestHeader("X-USER-ID") String UserId,
      @PathVariable Long productId) {

    boolean isDeleted = cartService.deleteItemFromCart(productId, UserId);
    if (isDeleted)
      return ResponseEntity.ok(Map.of(
          "status", 200,
          "message", "Items removed successfully"));
    return ResponseEntity.badRequest().body(Map.of(
        "status", 400,
        "message", "no item found or no user found"

    ));
  }

  @GetMapping
  public ResponseEntity<Map> getAllCartItem(@RequestHeader("X-USER-ID") String Userid) {
    return ResponseEntity.ok(Map.of(
        "success", true,
        "data", cartService.getAllCarts(Userid)

    ));
  }

}
