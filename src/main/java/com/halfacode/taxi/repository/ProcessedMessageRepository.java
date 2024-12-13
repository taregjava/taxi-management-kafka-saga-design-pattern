package com.halfacode.taxi.repository;

import com.halfacode.taxi.entity.ProcessedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessage, String> {

    boolean existsByMessageId(String messageId);
}
