package com.tomtom.controller;

import com.tomtom.exception.AppException;
import com.tomtom.model.ErrorResponse;
import com.tomtom.model.Item;
import com.tomtom.model.ShoppingCart;
import com.tomtom.model.dto.ItemDto;
import com.tomtom.model.dto.OrderDto;
import com.tomtom.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/order")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody ShoppingCart shoppingCart, @RequestParam String paymentMethod){
        return ResponseEntity.ok(shoppingCartService.placeOrder(shoppingCart.getUserId(), validate(shoppingCart), paymentMethod));
    }

    @PutMapping("/cart")
    public ResponseEntity<List<ItemDto>> addToCart(@RequestBody ShoppingCart shoppingCart) {
        return ResponseEntity.ok(shoppingCartService.addToCart(shoppingCart.getUserId(), validate(shoppingCart)));
    }

    private List<ItemDto> validate(ShoppingCart shoppingCart) {
        if (Objects.isNull(shoppingCart.getUserId())) {
            throw new AppException("Invalid Input", ErrorResponse.builder()
                    .errorMsg("Invalid input")
                    .status(HttpStatus.BAD_REQUEST)
                    .timestamp(Calendar.getInstance().getTime())
                    .build());
        }

        return shoppingCart.getItems().stream()
                .map(item -> populateItemDto(item))
                .collect(Collectors.toList());
    }

    private ItemDto populateItemDto(Item item) {
        return ItemDto.builder()
                .productId(item.getProductId())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();
    }
}
