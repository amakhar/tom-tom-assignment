package com.tomtom.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OrderDto {
    private String orderId;
    private String status;
    private Date orderDate;


}
