package com.example.cafeteria.service;

import com.example.cafeteria.domain.Product;
import com.example.cafeteria.domain.ProductCategory;
import com.example.cafeteria.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> list(String category, String search) {
        ProductCategory cat = null;
        if (StringUtils.hasText(category)) {
            cat = ProductCategory.valueOf(category.toUpperCase());
        }

        boolean hasSearch = StringUtils.hasText(search);

        if (cat == null && !hasSearch) {
            return productRepository.findByActiveTrue();
        } else if (cat != null && !hasSearch) {
            return productRepository.findByCategoryAndActiveTrue(cat);
        } else if (cat == null) { // solo búsqueda
            return productRepository.findByNameContainingIgnoreCaseAndActiveTrue(search);
        } else { // categoría + búsqueda
            return productRepository.findByCategoryAndNameContainingIgnoreCaseAndActiveTrue(cat, search);
        }
    }

    public Product findById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
    }

    public Product create(Product p) {
        validarProducto(p);
        p.setId(null);
        if (p.getPrice() == null) {
            p.setPrice(BigDecimal.ZERO);
        }
        p.setActive(true);
        return productRepository.save(p);
    }

    public Product update(String id, Product p) {
        Product existing = findById(id);
        validarProducto(p);

        existing.setName(p.getName());
        existing.setDescription(p.getDescription());
        existing.setPrice(p.getPrice());
        existing.setCategory(p.getCategory());
        existing.setActive(p.isActive());

        return productRepository.save(existing);
    }

    // Eliminación lógica: lo marcamos inactivo
    public void delete(String id) {
        Product existing = findById(id);
        existing.setActive(false);
        productRepository.save(existing);
    }

    private void validarProducto(Product p) {
        if (!StringUtils.hasText(p.getName())) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (p.getCategory() == null) {
            throw new IllegalArgumentException("La categoría del producto es obligatoria");
        }
        if (p.getPrice() == null || p.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio del producto es inválido");
        }
    }
}
