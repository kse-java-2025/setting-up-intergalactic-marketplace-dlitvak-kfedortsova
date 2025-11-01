package service.impl;

import domain.category.Category;
import domain.product.Product;
import service.ProductService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InMemoryProductService implements ProductService {

    private final Map<String, Product> storage = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public Product create(Product product) {
        String id = String.valueOf(idGenerator.getAndIncrement());
        Product saved = new Product(
                id,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory() != null ? product.getCategory() : List.of()
        );
        storage.put(id, saved);
        return saved;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Product update(String id, Product product) {
        Product updated = new Product(
                id,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory()
        );
        storage.put(id, updated);
        return updated;
    }

    @Override
    public void delete(String id) {
        storage.remove(id);
    }
}
