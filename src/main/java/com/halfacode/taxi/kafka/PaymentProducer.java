package com.halfacode.taxi.kafka;

import com.halfacode.taxi.dto.PaymentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {

    @Autowired
    private KafkaTemplate<String, PaymentMessage> kafkaTemplate;

    private static final String TOPIC = "payment_transactions";

    public void sendPayment(PaymentMessage message) {
        kafkaTemplate.send(TOPIC, message);
    }
}