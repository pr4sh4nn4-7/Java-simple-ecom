package com.Ecommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.Ecommerce.Model.Cart;
import com.Ecommerce.Model.Product;
import com.Ecommerce.Model.User;

public interface CartRepo extends JpaRepository<Cart, Long> {
  Cart findByUserAndProduct(User user, Product product);

  @Modifying
  void deleteByUserAndProduct(User user, Product product);

  List<Cart> findByUser(User user);

  @Modifying
  void deleteByUser(User user);

}
