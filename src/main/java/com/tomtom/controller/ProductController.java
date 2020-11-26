package com.tomtom.controller;

import com.tomtom.exception.AppException;
import com.tomtom.model.ErrorResponse;
import com.tomtom.model.Product;
import com.tomtom.model.dto.ProductDto;
import com.tomtom.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> fetchProducts(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) List<String> sort) {
        log.info("Sort order : " + sort);
        return ResponseEntity.ok(productService.fetchProducts(page, size, sort));
    }

    @PostMapping("/products")
    public ResponseEntity<Void> postProducts(@RequestBody List<Product> products) {
        List<ProductDto> productDtos = validate(products);
        productService.postProducts(productDtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private List<ProductDto> validate(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            if (isValid(product))
                productDtoList.add(populateProductDto(product));
            else {
                throw new AppException("Invalid Input for product", ErrorResponse.builder()
                        .errorMsg("Invalid input")
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(Calendar.getInstance().getTime())
                        .build());
            }
        }
        return productDtoList;
    }

    private ProductDto populateProductDto(Product product) {
        return ProductDto.builder()
                .productId(UUID.randomUUID().toString())
                .name(product.getName())
                .description(product.getDescription())
                .availableItemCount(product.getAvailableItemCount())
                .price(product.getPrice())
                .category(product.getCategory())
                .sellerId(product.getSellerId())
                .build();
    }

    private boolean isValid(Product product) {
        return product.getAvailableItemCount() > 0;
    }
}
