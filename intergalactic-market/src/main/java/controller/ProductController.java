package controller;

import domain.product.Product;
import dto.product.ProductDTO;
import dto.product.ProductListDTO;
import service.ProductService;
import service.mapper.ProductMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper mapper;

    public ProductController(ProductService productService, ProductMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductDTO dto) {
        Product product = mapper.toProduct(dto);
        Product saved = productService.create(product);
        return ResponseEntity
                .created(URI.create("/api/v1/products/" + saved.getId()))
                .body(mapper.toProductEntry(saved));
    }

    @GetMapping
    public ResponseEntity<ProductListDTO> findAll() {
        return ResponseEntity.ok(
                mapper.toProductListDTO(productService.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return productService.findById(id)
                .map(p -> ResponseEntity.ok(mapper.toProductEntry(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @Valid @RequestBody ProductDTO dto
    ) {
        Product product = mapper.toProduct(dto);
        return productService.findById(id)
                .map(existing -> {
                    Product updated = productService.update(id, product);
                    return ResponseEntity.ok(mapper.toProductEntry(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return productService.findById(id)
                .map(p -> {
                    productService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
