package com.Ecommerce.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Ecommerce.Model.Product;
import com.Ecommerce.Repository.ProductRepo;
import com.Ecommerce.dto.ProductRequest;
import com.Ecommerce.dto.ProductResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepo productRepo;

  public ProductResponse createProduct(ProductRequest productReqData) {
    Product product = new Product();
    updateProductFromRequest(product, productReqData);

    Product savedProduct = productRepo.save(product);
    return mapToProductResponse(savedProduct);

  }

  public Optional<ProductResponse> updateProduct(Long id, ProductRequest productReqData) {
    return productRepo.findById(id).map(existingproduct -> {
      updateProductFromRequest(existingproduct, productReqData);
      Product saveProduct = productRepo.save(existingproduct);
      return mapToProductResponse(saveProduct);
    });

  }

  private ProductResponse mapToProductResponse(Product product) {
    ProductResponse productResponse = new ProductResponse();
    productResponse.setId(product.getId());
    productResponse.setName(product.getName());
    productResponse.setCategory(product.getCategory());
    productResponse.setDescription(product.getDescription());
    productResponse.setImageUrl(product.getImageUrl());
    productResponse.setPrice(product.getPrice());
    productResponse.setStockQuantity(product.getStockQuantity());
    productResponse.setActive(product.getActive());
    return productResponse;

  }

  public boolean deleteProduct(Long id) {
    return productRepo.findById(id).map(product -> {
      product.setActive(false);
      productRepo.save(product);
      return true;
    }).orElse(false);

  }

  public List<ProductResponse> searchProduct(String keyword) {
    return productRepo.searchProdut(keyword).stream().map(this::mapToProductResponse).collect(Collectors.toList());

  }

  private void updateProductFromRequest(Product product, ProductRequest productReqData) {
    product.setName(productReqData.getName());
    product.setDescription(productReqData.getDescription());
    product.setCategory(productReqData.getCategory());
    product.setImageUrl(productReqData.getImageUrl());
    product.setPrice(productReqData.getPrice());
    product.setStockQuantity(productReqData.getStockQuantity());
  }

  public List<ProductResponse> getAllProducts() {
    return productRepo.findByActiveTrue().stream().map(this::mapToProductResponse).collect(Collectors.toList());
  }

}
