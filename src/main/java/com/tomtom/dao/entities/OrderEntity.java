package com.tomtom.dao.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "cust_order")
@Data
public class OrderEntity {

    @Id
    private String orderId;
    private String status;
    private Date orderDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private AccountEntity account;
}
