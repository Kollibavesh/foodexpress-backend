package com.bavesh.foodapp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private Double price;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // ===== GETTERS =====

    public Long getId() { return id; }

    public Integer getQuantity() { return quantity; }

    public Double getPrice() { return price; }

    public Order getOrder() { return order; }

    public Product getProduct() { return product; }

    // ===== SETTERS =====

    public void setId(Long id) { this.id = id; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public void setPrice(Double price) { this.price = price; }

    public void setOrder(Order order) { this.order = order; }

    public void setProduct(Product product) { this.product = product; }
}