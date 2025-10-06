package io.github.joaoVitorLeal.marketsphere.orders.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.joaoVitorLeal.marketsphere.orders.exception.MessagingSerializationException;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.mapper.OrderRepresentationMapper;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.representation.OrderRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentPublisher {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${market-sphere.config.kafka.topics.paid-orders}")
    private String topic;

    public void publish(OrderRepresentation orderRepresentation) {
        log.info("publishing paid order with ID: {}", orderRepresentation.orderId());
        try {
            String jsonPayload = objectMapper.writeValueAsString(orderRepresentation);
            String orderIdKey = String.valueOf(orderRepresentation.orderId());
            kafkaTemplate.send(topic, orderIdKey, jsonPayload);
        } catch (JsonProcessingException e) {
            log.error("Error serializing message for paid-orders topic. Order ID: {}", orderRepresentation.orderId(), e);
            throw new MessagingSerializationException("Error serializing message for Kafka", e);
        } catch (RuntimeException e) {
            log.error("Error sending message to paid-orders topic. Order ID: {}", orderRepresentation.orderId(), e);
            throw e;
        }
    }
}
