package com.tomtom.dao.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "account")
public class AccountEntity {

    @Id
    String userName;
    String password;
    String status;
    String email;
    String phone;
}
