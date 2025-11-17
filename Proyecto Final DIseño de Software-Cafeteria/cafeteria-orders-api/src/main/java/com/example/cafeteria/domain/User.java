package com.example.cafeteria.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String passwordHash;
    private UserRole role;
    private boolean active = true;
}
