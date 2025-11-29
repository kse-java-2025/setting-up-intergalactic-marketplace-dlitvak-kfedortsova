package com.cosmocats.intergalactic_market.service;

import com.cosmocats.intergalactic_market.domain.product.Product;
import com.cosmocats.intergalactic_market.service.impl.ProductServiceImpl;
import com.cosmocats.intergalactic_market.service.exception.ProductNotFoundException;
import com.cosmocats.intergalactic_market.product.ProductDTO;
import com.cosmocats.intergalactic_market.service.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductMapper mapper;

    @BeforeEach
    void cleanup() {
        productService.getAllProducts()
                .forEach(p -> productService.deleteProduct(p.getId()));
    }

    private ProductDTO newDto(String name, double price, String description, List<String> category) {
        return ProductDTO.builder()
                .name(name)
                .price(price)
                .description(description)
                .category(category)
                .build();
    }

    @Test
    @DisplayName("should create a new product")
    void shouldCreateProduct() {
        ProductDTO dto = newDto(
                "Cosmic Yarn",
                12.99,
                "Delicious cosmic yarn",
                List.of("SPACE_FOOD")
        );

        Product productToCreate = mapper.toProduct(dto);
        Product savedProduct = productService.createProduct(productToCreate);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Cosmic Yarn");

        Product found = productService.getProductById(savedProduct.getId());
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(savedProduct.getId());
    }

    @Test
    @DisplayName("should find all products")
    void shouldFindAllProducts() {
        ProductDTO dto1 = newDto("Yarn1", 5.0, "First yarn description", List.of("SPACE_FOOD"));
        ProductDTO dto2 = newDto("Yarn2", 6.0, "Second yarn description", List.of("SPACE_FOOD"));

        Product p1 = productService.createProduct(mapper.toProduct(dto1));
        Product p2 = productService.createProduct(mapper.toProduct(dto2));

        List<Product> products = productService.getAllProducts();
        assertThat(products).hasSize(2)
                .extracting(Product::getName)
                .containsExactlyInAnyOrder("Yarn1", "Yarn2");
    }

    @Test
    @DisplayName("should update existing product")
    void shouldUpdateProduct() {
        ProductDTO dto = newDto("Old Name", 10.0, "Old description", List.of("SPACE_FOOD"));
        Product saved = productService.createProduct(mapper.toProduct(dto));

        ProductDTO updateDto = newDto("New Name", 15.0, "Updated description", List.of("SPACE_FOOD"));
        Product updated = productService.updateProduct(saved.getId(), mapper.toProductForUpdate(updateDto));

        assertThat(updated.getId()).isEqualTo(saved.getId());
        assertThat(updated.getName()).isEqualTo("New Name");
        assertThat(updated.getPrice()).isEqualTo(15.0);
        assertThat(updated.getDescription()).isEqualTo("Updated description");
    }

    @Test
    @DisplayName("should delete existing product")
    void shouldDeleteProduct() {
        ProductDTO dto = newDto("DeleteMe", 7.0, "To be deleted", List.of("SPACE_FOOD"));
        Product saved = productService.createProduct(mapper.toProduct(dto));
        productService.deleteProduct(saved.getId());

        assertThrows(ProductNotFoundException.class, () ->
                productService.getProductById(saved.getId()));
    }

    @Test
    @DisplayName("should find product by id")
    void shouldFindById() {
        ProductDTO dto = newDto("FindMe", 3.0, "Find me in storage", List.of("SPACE_FOOD"));
        Product saved = productService.createProduct(mapper.toProduct(dto));

        Product found = productService.getProductById(saved.getId());
        assertThat(found).isNotNull()
                .isEqualTo(saved);
    }

    @Test
    @DisplayName("find by id returns empty if not found")
    void findByIdNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThrows(ProductNotFoundException.class, () ->
                productService.getProductById(randomId));
    }
}
