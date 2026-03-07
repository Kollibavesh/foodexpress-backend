package com.bavesh.foodapp;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;   // Link cart to logged-in user

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> items;

    public Long getId() { return id; }

    public String getUserEmail() { return userEmail; }

    public List<CartItem> getItems() { return items; }

    public void setId(Long id) { this.id = id; }

    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public void setItems(List<CartItem> items) { this.items = items; }
}