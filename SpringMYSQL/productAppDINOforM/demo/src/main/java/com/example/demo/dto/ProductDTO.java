package com.example.demo.dto;

import com.example.demo.model.Product;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Long categoryId;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.imageUrl = product.getImageUrl();
        this.price = product.getPrice();
        this.categoryId = product.getCategory().getId();
    }
}