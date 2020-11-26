package com.tomtom.dao.repository;

import com.tomtom.dao.entities.AccountEntity;
import com.tomtom.dao.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findByStatus(String status);
    List<CartEntity> findByAccount(AccountEntity accountEntity);
}
