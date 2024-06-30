package com.cibertec.colitafeliz.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWrapper {

    private UUID id;
    private String name;
    private String description;
    private double price;
    private String status;
    private Long categoryId;
    private String categoryName;

    public ProductWrapper(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductWrapper(UUID id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
