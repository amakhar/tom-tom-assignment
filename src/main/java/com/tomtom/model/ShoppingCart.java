package com.tomtom.model;

import lombok.Data;

import java.util.List;

@Data
public class ShoppingCart {
    private List<Item> items;
    private String userId;
}
