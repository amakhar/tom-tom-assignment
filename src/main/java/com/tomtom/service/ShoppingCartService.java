package com.tomtom.service;

import com.tomtom.model.dto.ItemDto;
import com.tomtom.model.dto.OrderDto;

import java.util.List;

public interface ShoppingCartService {
    List<ItemDto> addToCart(String userId, List<ItemDto> items);
    OrderDto placeOrder(String userId, List<ItemDto> items, String paymentMethod);
}
