package com.example.cafeteria.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String name;
    private ProductCategory category;
    private BigDecimal unitPrice;
    private boolean active = true;
}
