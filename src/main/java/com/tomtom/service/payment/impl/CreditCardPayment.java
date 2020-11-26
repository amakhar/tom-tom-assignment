package com.tomtom.service.payment.impl;

import com.tomtom.model.CreditCard;
import com.tomtom.service.payment.PaymentMethod;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreditCardPayment implements PaymentMethod {

    private String upiId;

    public CreditCardPayment(CreditCard creditCard) {
        this.upiId = upiId;
    }

    @Override
    public boolean pay(Double amount) {
        log.info("Successfully paid amount {} via Credit Card", amount);
        return false;
    }
}
