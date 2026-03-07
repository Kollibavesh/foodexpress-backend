package com.bavesh.foodapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // CREATE PRODUCT
    public Product createProduct(Product product) {

        // Always active when created
        product.setActive(true);

        return productRepository.save(product);
    }

    // GET ACTIVE PRODUCTS
    public Page<ProductResponse> getActiveProducts(
            int page, int size, String sort, String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort));

        return productRepository.findByActiveTrue(pageable)
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getImageUrl()
                ));
    }

    // SEARCH PRODUCTS
    public Page<ProductResponse> searchActiveProducts(
            String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findByNameContainingIgnoreCaseAndActiveTrue(keyword, pageable)
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getImageUrl()
                ));
    }

    // UPDATE PRODUCT
    public Product updateProduct(Long id, Product updatedProduct) {

        Product existing = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setImageUrl(updatedProduct.getImageUrl());

        return productRepository.save(existing);
    }

    // SOFT DELETE PRODUCT
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        product.setActive(false);

        productRepository.save(product);
    }
}