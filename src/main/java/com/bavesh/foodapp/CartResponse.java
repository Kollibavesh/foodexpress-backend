package com.bavesh.foodapp;

import java.util.List;

public class CartResponse {

    private String userEmail;
    private List<CartItemResponse> items;
    private double grandTotal;

    public CartResponse(String userEmail,
                        List<CartItemResponse> items,
                        double grandTotal) {
        this.userEmail = userEmail;
        this.items = items;
        this.grandTotal = grandTotal;
    }

    public String getUserEmail() { return userEmail; }
    public List<CartItemResponse> getItems() { return items; }
    public double getGrandTotal() { return grandTotal; }
}