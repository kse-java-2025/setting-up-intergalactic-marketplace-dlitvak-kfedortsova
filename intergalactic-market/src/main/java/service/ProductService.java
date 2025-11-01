package service;

import domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product create(Product product);
    List<Product> findAll();
    Optional<Product> findById(String id);
    Product update(String id, Product product);
    void delete(String id);
}
