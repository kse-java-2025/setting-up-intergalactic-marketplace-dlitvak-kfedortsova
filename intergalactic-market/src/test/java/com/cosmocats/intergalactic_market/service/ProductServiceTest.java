package com.cosmocats.intergalactic_market.service;

import com.cosmocats.intergalactic_market.domain.product.Product;
import com.cosmocats.intergalactic_market.service.exception.ProductNotFoundException;
import com.cosmocats.intergalactic_market.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ProductServiceImpl.class)
@DisplayName("ProductServiceImpl Tests")
class ProductServiceTest {

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl();
    }

    private Product newProduct(String name) {
        return Product.builder()
                .id(UUID.randomUUID())
                .name(name)
                .description("Test description")
                .price(10.0)
                .build();
    }

    @Test
    @DisplayName("Should create product successfully")
    void shouldCreateProduct() {
        Product product = newProduct("Cosmic Yarn");

        Product saved = productService.createProduct(product);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Cosmic Yarn");

        Product found = productService.getProductById(saved.getId());
        assertThat(found).isEqualTo(saved);
    }

    @Test
    @DisplayName("Should find all products")
    void shouldFindAllProducts() {
        productService.createProduct(newProduct("Yarn A"));
        productService.createProduct(newProduct("Yarn B"));

        List<Product> products = productService.getAllProducts();

        assertThat(products)
                .hasSize(2)
                .extracting(Product::getName)
                .containsExactlyInAnyOrder("Yarn A", "Yarn B");
    }

    @Test
    @DisplayName("Should update product successfully")
    void shouldUpdateProduct() {
        Product original = newProduct("Old Name");
        productService.createProduct(original);

        Product update = Product.builder()
                .name("New Name")
                .description("Updated")
                .price(20.0)
                .build();

        Product updated = productService.updateProduct(original.getId(), update);

        assertThat(updated.getId()).isEqualTo(original.getId());
        assertThat(updated.getName()).isEqualTo("New Name");
        assertThat(updated.getPrice()).isEqualTo(20.0);
    }

    @Test
    @DisplayName("Should delete product successfully")
    void shouldDeleteProduct() {
        Product product = newProduct("Delete Me");
        productService.createProduct(product);

        productService.deleteProduct(product.getId());

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(product.getId()));
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException when ID does not exist")
    void shouldThrowWhenNotFound() {
        UUID randomId = UUID.randomUUID();

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(randomId));
    }

    @Test
    @DisplayName("Should return empty list when no products exist")
    void shouldReturnEmptyList() {
        assertThat(productService.getAllProducts()).isEmpty();
    }
}
