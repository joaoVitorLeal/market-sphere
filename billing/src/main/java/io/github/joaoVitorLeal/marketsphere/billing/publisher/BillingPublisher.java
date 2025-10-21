package io.github.joaoVitorLeal.marketsphere.billing.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.joaoVitorLeal.marketsphere.billing.exception.MessagingSerializationException;
import io.github.joaoVitorLeal.marketsphere.billing.model.Order;
import io.github.joaoVitorLeal.marketsphere.billing.publisher.event.enums.OrderStatus;
import io.github.joaoVitorLeal.marketsphere.billing.publisher.event.OrderBilledEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BillingPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${market-sphere.config.kafka.topics.billed-orders}")
    private String topic;

    public BillingPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(Order order, String invoiceUrl) {
        try {
            OrderBilledEvent orderBilledEvent = new OrderBilledEvent(
                    order.orderId(),
                    OrderStatus.BILLED,
                    invoiceUrl
            );
            String jsonPayload = objectMapper.writeValueAsString(orderBilledEvent);
            String orderIdKey = String.valueOf(orderBilledEvent.orderId());
            kafkaTemplate.send(topic, orderIdKey, jsonPayload);

        } catch (JsonProcessingException e) {
            log.error("Error serializing message for billed-orders topic. Order ID: {}", order.orderId(), e);
            throw new MessagingSerializationException("Error serializing message for Kafka", e);
        } catch (RuntimeException e) {
            log.error("Error sending message to paid-orders topic. Order ID: {}", order.orderId(), e);
            throw e;
        }
    }
}
