package com.cosmocats.service;

import com.cosmocats.domain.product.Product;
import com.cosmocats.service.impl.InMemoryProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest {
    private InMemoryProductService productService;

    @BeforeEach
    void setUp() {
        productService = new InMemoryProductService();
    }

    @Test
    @DisplayName("should create a new product")
    void shouldCreateProduct() {
        Product productToCreate = Product.builder()
                .name("Cosmic Yarn")
                .price(12.99)
                .build();

        Product savedProduct = productService.create(productToCreate);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Cosmic Yarn");

        Optional<Product> found = productService.findById(savedProduct.getId());
        assertThat(found).isPresent();
    }

}
