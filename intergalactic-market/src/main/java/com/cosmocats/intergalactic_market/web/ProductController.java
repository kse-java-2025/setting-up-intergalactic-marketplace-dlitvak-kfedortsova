package com.cosmocats.intergalactic_market.web;

import com.cosmocats.intergalactic_market.domain.product.Product;
import com.cosmocats.intergalactic_market.product.ProductDTO;
import com.cosmocats.intergalactic_market.product.ProductEntry;
import com.cosmocats.intergalactic_market.product.ProductListDTO;
import com.cosmocats.intergalactic_market.service.ProductService;
import com.cosmocats.intergalactic_market.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import static java.net.URI.create;
import java.util.UUID;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<ProductListDTO> getAllProducts() {
        return ResponseEntity.ok(productMapper.toProductListDTO(productService.getAllProducts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntry> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productMapper.toProductEntry(productService.getProductById(id)));
    }

    @PostMapping
    public ResponseEntity<ProductEntry> createProduct(@Valid @RequestBody ProductDTO dto) {
        Product product = productMapper.toProduct(dto);
        Product saved = productService.createProduct(product);
        return ResponseEntity
                .created(create("/api/v1/products/" + saved.getId()))
                .body(productMapper.toProductEntry(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntry> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductDTO dto
    ) {
        Product productUpdate = productMapper.toProductForUpdate(dto);
        Product updated = productService.updateProduct(id, productUpdate);

        return ResponseEntity.ok(productMapper.toProductEntry(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return noContent().build();
    }
}
