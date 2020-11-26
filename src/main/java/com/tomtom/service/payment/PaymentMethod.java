package com.tomtom.service.payment;

public interface PaymentMethod {
    enum Option{
        UPI, CREDIT_CARD
    }
    boolean pay(Double amount);
}
