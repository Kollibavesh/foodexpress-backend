package com.bavesh.foodapp;

import com.bavesh.foodapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    // ==============================
    // PLACE ORDER
    // ==============================
    public Order placeOrder(String userEmail) {

        Cart cart = cartRepository.findByUserEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found for user: " + userEmail));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new ResourceNotFoundException("Cannot place order. Cart is empty.");
        }

        // Create new order
        Order order = new Order();
        order.setUserEmail(userEmail);
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        // Convert CartItems -> OrderItems
        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());

            orderItemRepository.save(orderItem);
        }

        // Clear cart after order placement
        cartItemRepository.deleteByCartId(cart.getId());

        return savedOrder;
    }

    // ==============================
    // UPDATE ORDER STATUS
    // ==============================
    public Order updateOrderStatus(Long orderId, OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found with id: " + orderId));

        order.setStatus(status);
        return orderRepository.save(order);
    }

    // ==============================
    // GET USER ORDERS
    // ==============================
    public List<Order> getOrdersByUser(String userEmail) {

        List<Order> orders = orderRepository.findByUserEmail(userEmail);

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for user: " + userEmail);
        }

        return orders;
    }

    // ==============================
    // GET ALL ORDERS (ADMIN)
    // ==============================
    public List<Order> getAllOrders() {

        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found in system.");
        }

        return orders;
    }
}