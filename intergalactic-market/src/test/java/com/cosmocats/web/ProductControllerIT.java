package com.cosmocats.web;

import com.cosmocats.controller.ProductController;
import com.cosmocats.domain.product.Product;
import com.cosmocats.dto.product.ProductDTO;
import com.cosmocats.service.ProductService;
import com.cosmocats.service.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
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

        when(productMapper.toProduct(dto)).thenReturn(product);
        when(productService.create(product)).thenReturn(saved);
        when(productMapper.toProductEntry(saved)).thenReturn(MapToEntry(saved));

        mvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/api/v1/products/")))
                .andExpect(jsonPath("$.id").value(saved.getId().toString()))
                .andExpect(jsonPath("$.name").value("Galaxy Milk"));
    }
}
