package com.bavesh.foodapp;

import com.bavesh.foodapp.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    // ==========================
    // PLACE ORDER
    // ==========================
    @PostMapping("/place")
    public ApiResponse<Order> placeOrder(@RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        Order order = orderService.placeOrder(email);

        return new ApiResponse<>(true, "Order placed successfully", order);
    }

    // ==========================
    // GET ORDERS (USER)
    // ==========================
    @GetMapping("/my")
    public ApiResponse<List<Order>> getMyOrders(@RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        List<Order> orders = orderService.getOrdersByUser(email);

        return new ApiResponse<>(true, "Orders fetched successfully", orders);
    }

    // ==========================
    // GET ALL ORDERS (ADMIN)
    // ==========================
    @GetMapping("/all")
    public ApiResponse<List<Order>> getAllOrders() {

        List<Order> orders = orderService.getAllOrders();

        return new ApiResponse<>(true, "All orders fetched successfully", orders);
    }

    // ==========================
    // UPDATE ORDER STATUS (ADMIN)
    // ==========================
    @PutMapping("/{orderId}/status")
    public ApiResponse<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {

        Order updatedOrder = orderService.updateOrderStatus(orderId, status);

        return new ApiResponse<>(true, "Order status updated", updatedOrder);
    }
}