package com.cosmocats.intergalactic_market.web;

import com.cosmocats.intergalactic_market.product.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // 4. ADD this to reset the InMemoryProductService for each test
class ProductControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("POST /api/v1/products - success")
    void createProductSuccess() throws Exception {
        ProductDTO dto = new ProductDTO("Galaxy Milk", 8.5, "tasty cosmic milk", List.of("SPACE_FOOD"));

        MvcResult result = mvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/v1/products/")))
                .andExpect(jsonPath("$.name").value("Galaxy Milk"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        String id = com.jayway.jsonpath.JsonPath.read(jsonResponse, "$.id");

        mvc.perform(get("/api/v1/products/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Galaxy Milk"));
    }

    @Test
    @DisplayName("GET /api/v1/products/{id} - not found")
    void findProductByIdNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        mvc.perform(get("/api/v1/products/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/products/{id} - success")
    void deleteProductSuccess() throws Exception {
        // add new product to test deletion
        ProductDTO dto = new ProductDTO("Galaxy Milk 2", 8.5, "tasty cosmic milk", List.of("SPACE_FOOD"));
        MvcResult result = mvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        String id = com.jayway.jsonpath.JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        // delete the created product
        mvc.perform(delete("/api/v1/products/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // check if it is deleted by accessing it
        mvc.perform(get("/api/v1/products/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/products/{id} - not found")
    void deleteProductNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        mvc.perform(delete("/api/v1/products/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}