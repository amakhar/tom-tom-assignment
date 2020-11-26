package com.tomtom.dao.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "product")
@NoArgsConstructor
public class ProductEntity {

    @Id
    private String productId;
    private String name;
    private String description;
    private Double price;
    private Integer availableItemCount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category")
    private ProductCategoryEntity productCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id")
    private AccountEntity account;
}
