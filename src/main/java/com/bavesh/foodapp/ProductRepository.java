package com.bavesh.foodapp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // get only active products
    Page<Product> findByActiveTrue(Pageable pageable);

    // search active products
    Page<Product> findByNameContainingIgnoreCaseAndActiveTrue(String keyword, Pageable pageable);

}