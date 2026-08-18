package com.Ecommerce.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Ecommerce.dto.OrderItemDTO;
import com.Ecommerce.dto.OrderRequest;
import com.Ecommerce.dto.OrderResponse;
import com.Ecommerce.Model.Cart;
import com.Ecommerce.Model.Order;
import com.Ecommerce.Model.OrderItem;
import com.Ecommerce.Model.OrderStatus;
import com.Ecommerce.Model.User;
import com.Ecommerce.Repository.OrderRepository;
import com.Ecommerce.Repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final CartService cartService;
  private final UserRepo userRepo;
  private final OrderRepository orderRepository;

  public Optional<OrderResponse> createOrder(String userId) {
    // validate for cart items
    // validate for user
    // calculate total price
    // create order
    // clear the cart
    List<Cart> cartItems = cartService.getAllCarts(userId);
    if (cartItems.isEmpty()) {
      return Optional.empty();

    }
    Optional<User> userOptional = userRepo.findById(Long.parseLong(userId));
    if (userOptional.isEmpty()) {

      return Optional.empty();

    }
    User user = userOptional.get();
    // calculate total price

    BigDecimal totalPrice = cartItems.stream().map(item -> item.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    // creation of order
    Order order = new Order();
    order.setUser(user);
    order.setStatus(OrderStatus.CONFIRMED);
    order.setTotalAmount(totalPrice);
    List<OrderItem> orderItems = cartItems.stream().map(item -> new OrderItem(null,
        item.getProduct(),
        item.getQuantity(),
        item.getPrice(),
        order)).collect(Collectors.toList());

    order.setItems(orderItems);
    Order saveOrder = orderRepository.save(order);
    // clear cart
    cartService.clearCart(userId);
    return Optional.of(mapToOrderResponse(saveOrder));
  }

  private OrderResponse mapToOrderResponse(Order order) {
    return new OrderResponse(
        order.getId(),
        order.getTotalAmount(),
        order.getStatus(),
        order.getItems().stream()
            .map(item -> new OrderItemDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getQuantity(),
                item.getPrice(),
                item.getPrice().multiply(new BigDecimal(item.getQuantity()))))
            .toList(),
        order.getCreatedAt());

  }

}
