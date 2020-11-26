package com.tomtom.service.impl;

import com.tomtom.dao.entities.ProductEntity;
import com.tomtom.dao.repository.AccountRepository;
import com.tomtom.dao.repository.ProductCategoryRepository;
import com.tomtom.dao.repository.ProductRepository;
import com.tomtom.model.dto.ProductDto;
import com.tomtom.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDto> fetchProducts(int page, int size, List<String> sort) {
        List<Sort.Order> sortOrders = generateSortOrders(sort);
        return populateProducts(productRepository.findAll(PageRequest.of(page, size, Sort.by(sortOrders))));
    }

    private List<ProductDto> populateProducts(Page<ProductEntity> page) {
        return page.stream()
                .map(entity -> populateProduct(entity))
                .collect(Collectors.toList());
    }

    private ProductDto populateProduct(ProductEntity entity) {
        return ProductDto.builder()
                .productId(entity.getProductId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getProductCategory().getName())
                .price(entity.getPrice())
                .availableItemCount(entity.getAvailableItemCount())
                .sellerId(entity.getAccount().getUserName())
                .build();
    }

    private List<Sort.Order> generateSortOrders(List<String> sort) {

        if (CollectionUtils.isEmpty(sort)) {
            return List.of(new Sort.Order(Sort.Direction.ASC, "price"));
        }

        return sort.stream()
                .map(sortOrder -> {
                    if (sortOrder.contains(",")) {
                        String[] temp = sortOrder.split(",");
                        return new Sort.Order(getDirection(temp[1]), temp[0]);
                    }
                    return new Sort.Order(Sort.Direction.ASC, sortOrder);
                })
                .collect(Collectors.toList());
    }

    private Sort.Direction getDirection(String direction) {
        return Sort.Direction.DESC.name().equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    @Override
    public boolean postProducts(List<ProductDto> productDtos) {
        List<ProductEntity> productEntities = populateProductEntities(productDtos);
        productRepository.saveAll(productEntities);
        return Boolean.TRUE;
    }

    private List<ProductEntity> populateProductEntities(List<ProductDto> productDtos) {
        return productDtos.stream()
                .map(productDto -> populateProductEntity(productDto))
                .collect(Collectors.toList());
    }

    private ProductEntity populateProductEntity(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(productDto.getProductId());
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setProductCategory(productCategoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new RuntimeException("Product category " + productDto.getCategory() + "does not exists")));
        productEntity.setAvailableItemCount(productDto.getAvailableItemCount());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setAccount(accountRepository.findById(productDto.getSellerId())
                .orElseThrow(() -> new RuntimeException("Invalid seller")));

        return productEntity;
    }
}
