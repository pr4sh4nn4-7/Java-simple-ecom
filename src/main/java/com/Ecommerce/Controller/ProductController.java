package com.Ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Ecommerce.Service.ProductService;
import com.Ecommerce.dto.ProductRequest;
import com.Ecommerce.dto.ProductResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/product")
public class ProductController {
  private final ProductService productService;

  @PostMapping("create")
  public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest userReqData) {
    return new ResponseEntity<>(productService.createProduct(userReqData), HttpStatus.CREATED);

  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest userReqData) {
    return productService.updateProduct(id, userReqData).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.badRequest().build());

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    boolean deleted = productService.deleteProduct(id);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

  }

  @GetMapping("/search")
  public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String keyword) {
    return ResponseEntity.ok(productService.searchProduct(keyword));
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> getProducts() {
    return ResponseEntity.ok(productService.getAllProducts());
  }
}
