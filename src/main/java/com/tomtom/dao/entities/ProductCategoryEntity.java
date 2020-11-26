package com.tomtom.dao.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "product_category")
@Data
@NoArgsConstructor
public class ProductCategoryEntity {

    @Id
    private String name;
    private String description;
}
