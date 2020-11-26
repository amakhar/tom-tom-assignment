package com.tomtom.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategory {
    private String name;
    private String description;
}
