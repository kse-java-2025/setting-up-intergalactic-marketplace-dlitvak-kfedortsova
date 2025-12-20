package com.cosmocats.intergalactic_market.service;

import com.cosmocats.intergalactic_market.domain.product.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(UUID id);
    Product updateProduct(UUID id, Product product);
    void deleteProduct(UUID id);
}
