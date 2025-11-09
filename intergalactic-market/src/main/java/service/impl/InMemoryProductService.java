package service.impl;

import domain.category.Category;
import domain.product.Product;
import service.ProductService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InMemoryProductService implements ProductService {

    private final Map<UUID, Product> storage = new HashMap<>();

    @Override
    public Product create(Product product) {
        UUID id = UUID.randomUUID();
        Product saved = product.toBuilder().id(id).build();
        storage.put(id, saved);
        return saved;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Product update(UUID id, Product product) {
        Product updated = product.toBuilder().id(id).build();
        storage.put(id, updated);
        return updated;
    }

    @Override
    public void delete(UUID id) {
        storage.remove(id);
    }
}
