package com.example.cafeteria.service;

import com.example.cafeteria.domain.Product;
import com.example.cafeteria.domain.ProductCategory;
import com.example.cafeteria.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> listAllActive() {
        return productRepository.findByActiveTrue();
    }

    public List<Product> listByCategory(ProductCategory category) {
        return productRepository.findByCategory(category);
    }

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }
}
