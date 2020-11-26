package com.tomtom.model;

import lombok.Data;

@Data
public class Item {
    private String productId;
    private Integer quantity;
    private Double price;
}
