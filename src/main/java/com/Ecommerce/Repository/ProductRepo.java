package com.Ecommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Ecommerce.Model.Product;
import com.Ecommerce.dto.ProductResponse;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
  List<Product> findByActiveTrue();

  @Query("SELECT p from Product p WHERE p.active=true AND p.stockQuantity > 0 AND LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword, '%'))")
  List<Product> searchProdut(@Param("keyword") String keyword);

}
