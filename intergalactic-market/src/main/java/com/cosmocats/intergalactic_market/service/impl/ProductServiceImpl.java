package com.cosmocats.intergalactic_market.service.impl;

import com.cosmocats.intergalactic_market.domain.product.Product;
import com.cosmocats.intergalactic_market.service.ProductService;
import com.cosmocats.intergalactic_market.service.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final Map<UUID, Product> storage = new HashMap<>();

    @Override
    public Product createProduct(Product product) {
        storage.put(product.getId(), product);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Product getProductById(UUID id) {
        return Optional.ofNullable(storage.get(id))
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }

    @Override
    public Product updateProduct(UUID id, Product product) {
        if (!storage.containsKey(id)) {
            throw new ProductNotFoundException(id.toString());
        }

        Product updated = product.toBuilder().id(id).build();
        storage.put(id, updated);
        return updated;
    }

    @Override
    public void deleteProduct(UUID id) {
        storage.remove(id);
    }
}
