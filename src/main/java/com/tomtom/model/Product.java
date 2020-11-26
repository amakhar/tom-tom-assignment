package com.tomtom.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private String name;
    private String description;
    private Double price;
    private Integer availableItemCount;
    private String category;
    private String sellerId;
}
