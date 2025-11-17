package com.example.cafeteria.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "promotions")
public class Promotion {

    @Id
    private String id;

    private String name;
    private String description;
    private int minOrdersCount;
    private BigDecimal discountPercentage;
    private boolean active = true;
}
