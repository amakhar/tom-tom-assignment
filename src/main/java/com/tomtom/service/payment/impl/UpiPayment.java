package com.tomtom.service.payment.impl;

import com.tomtom.service.payment.PaymentMethod;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpiPayment implements PaymentMethod {

    private String upiId;

    public UpiPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean pay(Double amount) {
        log.info("Successfully paid amount {} via UPI", amount);
        return false;
    }
}
