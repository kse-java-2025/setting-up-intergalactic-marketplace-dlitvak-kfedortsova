package com.cosmocats.intergalactic_market.web;

import com.cosmocats.intergalactic_market.controller.ProductController;
import com.cosmocats.intergalactic_market.domain.product.Product;
import com.cosmocats.intergalactic_market.product.ProductDTO;
import com.cosmocats.intergalactic_market.product.ProductEntry;
import com.cosmocats.intergalactic_market.product.ProductListDTO;
import com.cosmocats.intergalactic_market.service.ProductService;
import com.cosmocats.intergalactic_market.service.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @Test
    @DisplayName("POST /api/v1/products - success")
    void createProductSuccess() throws Exception {
        ProductDTO dto = new ProductDTO("Galaxy Milk", 8.5, "tasty cosmic milk", List.of("drinks"));
        Product product = Product.builder().name("Galaxy Milk").price(8.5).build();
        Product saved = product.toBuilder().id(UUID.randomUUID()).build();

        ProductEntry entry = new ProductEntry(
                saved.getId(),
                saved.getName(),
                saved.getPrice(),
                saved.getDescription(),
                dto.getCategory()
        );

        when(productMapper.toProduct(dto)).thenReturn(product);
        when(productService.create(product)).thenReturn(saved);
        when(productMapper.toProductEntry(saved)).thenReturn(entry);

        mvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/api/v1/products/")))
                .andExpect(jsonPath("$.id").value(saved.getId().toString()))
                .andExpect(jsonPath("$.name").value("Galaxy Milk"));
    }

    @Test
    @DisplayName("GET /api/v1/products/{id} - found")
    void findProductByIdFound() throws Exception {
        UUID id = UUID.randomUUID();
        Product product = Product.builder().id(id).name("Milk").price(5.0).description("Tasty").build();
        ProductEntry entry = new ProductEntry(id, product.getName(), product.getPrice(), product.getDescription(), List.of("drinks"));

        when(productService.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.toProductEntry(product)).thenReturn(entry);

        mvc.perform(get("/api/v1/products/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Milk"));
    }

    @Test
    @DisplayName("GET /api/v1/products/{id} - not found")
    void findProductByIdNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(productService.findById(id)).thenReturn(Optional.empty());

        mvc.perform(get("/api/v1/products/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/products/{id} - success")
    void deleteProductSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        Product product = Product.builder().id(id).name("Milk").price(5.0).description("Tasty").build();

        when(productService.findById(id)).thenReturn(Optional.of(product));
        doNothing().when(productService).delete(id);

        mvc.perform(delete("/api/v1/products/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/products/{id} - not found")
    void deleteProductNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(productService.findById(id)).thenReturn(Optional.empty());

        mvc.perform(delete("/api/v1/products/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
