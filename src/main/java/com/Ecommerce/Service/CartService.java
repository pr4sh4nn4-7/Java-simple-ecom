package com.Ecommerce.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Ecommerce.Model.Cart;
import com.Ecommerce.Model.Product;
import com.Ecommerce.Model.User;
import com.Ecommerce.Repository.CartRepo;
import com.Ecommerce.Repository.ProductRepo;
import com.Ecommerce.Repository.UserRepo;
import com.Ecommerce.dto.CartRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class CartService {
  private final CartRepo cartRepo;
  private final ProductRepo productRepo;
  private final UserRepo userRepo;

  public boolean addToCart(String userId, CartRequest request) {
    // look for produuct
    Optional<Product> productData = productRepo.findById(request.getProductId());
    if (productData.isEmpty()) {
      return false;

    }
    // save the db daata to product
    Product product = productData.get();

    if (product.getStockQuantity() < request.getQuantity()) {
      return false;
    }

    Optional<User> userOpt = userRepo.findById(Long.parseLong(userId));
    if (userOpt.isEmpty()) {
      return false;
    }
    // save the db data to user
    User user = userOpt.get();

    Cart existingCartItem = cartRepo.findByUserAndProduct(user, product);
    if (existingCartItem != null) {
      // Update the quantity
      existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
      existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
      cartRepo.save(existingCartItem);

    } else {
      // create new cart item
      Cart cartItem = new Cart();
      cartItem.setUser(user);
      cartItem.setProduct(product);
      cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
      cartItem.setQuantity(request.getQuantity());
      cartRepo.save(cartItem);
    }
    return true;

  }

  @Transactional
  public boolean deleteItemFromCart(Long productId, String userId) {

    Optional<Product> productData = productRepo.findById(productId);
    if (productData.isEmpty()) {
      return false;
    }

    Optional<User> userOpt = userRepo.findById(Long.parseLong(userId));
    if (userOpt.isEmpty()) {
      return false;
    }

    cartRepo.deleteByUserAndProduct(
        userOpt.get(),
        productData.get());

    return true;
  }

  public List<Cart> getAllCarts(String UserId) {
    return userRepo.findById(Long.parseLong(UserId)).map(cartRepo::findByUser).orElse(List.of());

  }

  @Transactional
  public void clearCart(String userId) {
    userRepo.findById(Long.parseLong(userId)).ifPresent(user -> cartRepo.deleteByUser(user));
    ;

  }

  // private <U> U findByUser(User user1) {
  // return null;
  // }

}
