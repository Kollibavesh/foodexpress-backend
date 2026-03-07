package com.bavesh.foodapp;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Long getId() { return id; }

    public int getQuantity() { return quantity; }

    public Product getProduct() { return product; }

    public Cart getCart() { return cart; }

    public void setId(Long id) { this.id = id; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void setProduct(Product product) { this.product = product; }

    public void setCart(Cart cart) { this.cart = cart; }
}