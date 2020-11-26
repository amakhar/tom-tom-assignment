package com.tomtom.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {
    private String productId;
    private Integer quantity;
    private Double price;
}
