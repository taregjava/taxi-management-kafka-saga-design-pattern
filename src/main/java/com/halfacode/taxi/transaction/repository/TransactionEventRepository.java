package com.halfacode.taxi.transaction.repository;

import com.halfacode.taxi.transaction.entity.TransactionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionEventRepository extends JpaRepository<TransactionEvent, String> {
}
