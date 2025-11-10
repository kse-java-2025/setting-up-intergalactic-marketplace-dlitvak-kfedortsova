package com.cosmocats.intergalactic_market.service;

import com.cosmocats.intergalactic_market.domain.product.Product;
import com.cosmocats.intergalactic_market.service.impl.InMemoryProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("should find all products")
    void shouldFindAllProducts() {
        Product p1 = productService.create(Product.builder().name("Yarn1").price(5.0).build());
        Product p2 = productService.create(Product.builder().name("Yarn2").price(6.0).build());

        List<Product> products = productService.findAll();
        assertThat(products).hasSize(2)
                .extracting(Product::getName)
                .containsExactlyInAnyOrder("Yarn1", "Yarn2");
    }

    @Test
    @DisplayName("should update existing product")
    void shouldUpdateProduct() {
        Product saved = productService.create(Product.builder().name("Old Name").price(10.0).build());
        Product updateData = Product.builder().name("New Name").price(15.0).build();

        Product updated = productService.update(saved.getId(), updateData);

        assertThat(updated.getId()).isEqualTo(saved.getId());
        assertThat(updated.getName()).isEqualTo("New Name");
        assertThat(updated.getPrice()).isEqualTo(15.0);
    }

    @Test
    @DisplayName("should delete existing product")
    void shouldDeleteProduct() {
        Product saved = productService.create(Product.builder().name("DeleteMe").price(7.0).build());
        productService.delete(saved.getId());

        Optional<Product> found = productService.findById(saved.getId());
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("should find product by id")
    void shouldFindById() {
        Product saved = productService.create(Product.builder().name("FindMe").price(3.0).build());

        Optional<Product> found = productService.findById(saved.getId());
        assertThat(found).isPresent()
                .contains(saved);
    }

    @Test
    @DisplayName("find by id returns empty if not found")
    void findByIdNotFound() {
        Optional<Product> found = productService.findById(UUID.randomUUID());
        assertThat(found).isEmpty();
    }
}
