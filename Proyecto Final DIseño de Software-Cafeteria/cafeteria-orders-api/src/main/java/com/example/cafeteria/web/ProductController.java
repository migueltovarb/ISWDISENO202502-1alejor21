package com.example.cafeteria.web;

import com.example.cafeteria.domain.Product;
import com.example.cafeteria.domain.ProductCategory;
import com.example.cafeteria.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        Product created = productService.save(product);
        return ResponseEntity.created(URI.create("/api/products/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id,
                                          @Valid @RequestBody Product product) {
        product.setId(id);
        Product updated = productService.save(product);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public List<Product> listActive() {
        return productService.listAllActive();
    }

    @GetMapping("/category/{category}")
    public List<Product> listByCategory(@PathVariable ProductCategory category) {
        return productService.listByCategory(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable String id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
