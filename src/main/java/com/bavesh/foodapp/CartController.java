package com.bavesh.foodapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add product to cart
    @PostMapping("/add")
    public String addToCart(Authentication authentication,
                            @RequestParam Long productId,
                            @RequestParam int quantity) {

        String userEmail = authentication.getName();
        cartService.addToCart(userEmail, productId, quantity);

        return "Product added to cart";
    }

    // View cart
    @GetMapping
    public CartResponse viewCart(Authentication authentication) {

        String userEmail = authentication.getName();
        return cartService.viewCart(userEmail);
    }

    // Clear cart
    @DeleteMapping("/clear")
    public String clearCart(Authentication authentication) {

        String userEmail = authentication.getName();
        cartService.clearCart(userEmail);

        return "Cart cleared";
    }
    @DeleteMapping("/remove")
    public String removeItem(Authentication authentication,
                             @RequestParam Long productId) {

        String userEmail = authentication.getName();
        cartService.removeFromCart(userEmail, productId);

        return "Item removed from cart";
    }
}