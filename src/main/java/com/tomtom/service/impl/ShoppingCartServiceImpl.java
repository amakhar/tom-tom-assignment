package com.tomtom.service.impl;

import com.tomtom.dao.entities.AccountEntity;
import com.tomtom.dao.entities.CartEntity;
import com.tomtom.dao.entities.OrderEntity;
import com.tomtom.dao.repository.AccountRepository;
import com.tomtom.dao.repository.CartRepository;
import com.tomtom.dao.repository.OrderRepository;
import com.tomtom.dao.repository.ProductRepository;
import com.tomtom.exception.AppException;
import com.tomtom.model.CartItemStatus;
import com.tomtom.model.CreditCard;
import com.tomtom.model.ErrorResponse;
import com.tomtom.model.OrderStatus;
import com.tomtom.model.dto.ItemDto;
import com.tomtom.model.dto.OrderDto;
import com.tomtom.service.ShoppingCartService;
import com.tomtom.service.payment.PaymentMethod;
import com.tomtom.service.payment.impl.CreditCardPayment;
import com.tomtom.service.payment.impl.UpiPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ItemDto> addToCart(String userId, List<ItemDto> items) {
        cartRepository.saveAll(populateCartEntities(userId, items));
        return populateItemDtos(cartRepository.findByStatus(CartItemStatus.CART.name()));
    }

    @Transactional
    @Override
    public OrderDto placeOrder(String userId, List<ItemDto> items, String paymentMethod) {
        List<CartEntity> cartEntities = cartRepository.findByAccount(populateAccountEntity(userId));
        OrderEntity orderEntity = orderRepository.save(populateOrderEntity(userId));
        cartRepository.saveAll(updateItemStatus(cartEntities, orderEntity));
        makePayment(userId, paymentMethod);
        return populateOrderDto(orderEntity);
    }

    private void makePayment(String userId, String paymentMethod) {
        //Fetch account payment information for userId for paymentMethod
        AccountEntity accountEntity = new AccountEntity();
        getPaymentMethod(paymentMethod, accountEntity);
    }

    private PaymentMethod getPaymentMethod(String paymentMethod, AccountEntity entity) {
        if (PaymentMethod.Option.UPI.name().equals(paymentMethod))
            return new UpiPayment("");
        if (PaymentMethod.Option.CREDIT_CARD.name().equals(paymentMethod))
            return new CreditCardPayment(CreditCard.builder().build());

        throw new AppException("Invalid payment option provided",
                ErrorResponse.builder().timestamp(Calendar.getInstance().getTime()).build());
    }

    private AccountEntity populateAccountEntity(String userId) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserName(userId);
        return accountEntity;
    }


    private List<CartEntity> updateItemStatus(List<CartEntity> cartEntities, OrderEntity orderEntity) {
        return cartEntities.stream().map(cartEntity -> {
            cartEntity.setStatus(CartItemStatus.ORDERED.name());
            cartEntity.setOrder(orderEntity);
            return cartEntity;
        }).collect(Collectors.toList());
    }

    private OrderDto populateOrderDto(OrderEntity orderEntity) {
        return OrderDto.builder()
                .orderId(orderEntity.getOrderId())
                .status(orderEntity.getStatus())
                .orderDate(orderEntity.getOrderDate())
                .build();
    }

    private OrderEntity populateOrderEntity(String userId) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(UUID.randomUUID().toString());
        orderEntity.setStatus(OrderStatus.ORDERED.name());
        orderEntity.setAccount(populateAccountEntity(userId));
        orderEntity.setOrderDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        return orderEntity;
    }

    private List<String> extractProductIds(List<ItemDto> items) {
        return items.stream().map(ItemDto::getProductId).collect(Collectors.toList());
    }

    private List<ItemDto> populateItemDtos(List<CartEntity> cartEntities) {
        return cartEntities.stream()
                .map(cartEntity -> populateItemDto(cartEntity))
                .collect(Collectors.toList());
    }

    private ItemDto populateItemDto(CartEntity cartEntity) {
        return ItemDto.builder()
                .productId(cartEntity.getProduct().getProductId())
                .quantity(cartEntity.getQuantity())
                .price(cartEntity.getPrice())
                .build();
    }

    private List<CartEntity> populateCartEntities(String userId, List<ItemDto> items) {
        return items.stream()
                .map(itemDto -> populateCartEntity(userId, itemDto))
                .collect(Collectors.toList());
    }

    private CartEntity populateCartEntity(String userId, ItemDto itemDto) {
        CartEntity entity = new CartEntity();
        entity.setAccount(accountRepository.findById(userId).orElseThrow(() ->
                new AppException("Invalid Input for product", ErrorResponse.builder()
                        .errorMsg("Invalid userId")
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(Calendar.getInstance().getTime())
                        .build())));
        entity.setProduct(productRepository.findById(itemDto.getProductId()).orElseThrow(() ->
                new AppException("Invalid Input for product", ErrorResponse.builder()
                        .errorMsg("Invalid productId")
                        .status(HttpStatus.BAD_REQUEST)
                        .timestamp(Calendar.getInstance().getTime())
                        .build())));
        entity.setPrice(itemDto.getPrice());
        entity.setQuantity(itemDto.getQuantity());
        entity.setStatus(CartItemStatus.CART.name());

        return entity;
    }
}
