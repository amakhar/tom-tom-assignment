package com.tomtom.service;

import com.tomtom.model.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> fetchProducts(int page, int size, List<String> sort);

    boolean postProducts(List<ProductDto> productDtos);
}
