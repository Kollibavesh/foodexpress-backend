package com.bavesh.foodapp;

import com.bavesh.foodapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addToCart(String userEmail, Long productId, int quantity) {

        Cart cart = cartRepository.findByUserEmail(userEmail)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserEmail(userEmail);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }

        cartItemRepository.save(cartItem);
    }

    public CartResponse viewCart(String userEmail) {

        Cart cart = cartRepository.findByUserEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> new CartItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity()
                ))
                .toList();

        double total = items.stream()
                .mapToDouble(CartItemResponse::getTotalPrice)
                .sum();

        return new CartResponse(userEmail, items, total);
    }

    public void clearCart(String userEmail) {

        Cart cart = cartRepository.findByUserEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        cartItemRepository.deleteByCartId(cart.getId());
    }
    public void removeFromCart(String userEmail, Long productId) {

        Cart cart = cartRepository.findByUserEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item not found in cart"));

        cartItemRepository.delete(cartItem);
    }
}