package com.example.cafeteria.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    // Nombre visible en el menú
    @Indexed
    private String name;

    // Descripción corta opcional
    private String description;

    // Categoría (HU001/HU002)
    private ProductCategory category;

    // Precio unitario
    private BigDecimal price;

    // Si está disponible o no en el menú
    private boolean active;
}
