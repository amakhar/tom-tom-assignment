package com.tomtom.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private String productId;
    private String name;
    private String description;
    private Double price;
    private Integer availableItemCount;
    private String category;
    private String sellerId;
}
