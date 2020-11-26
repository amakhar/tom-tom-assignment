package com.tomtom.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCard {
    private String name;
    private String cardNumber;
    private String expirationDate;
}
