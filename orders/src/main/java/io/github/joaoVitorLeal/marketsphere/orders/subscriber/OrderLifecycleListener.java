package io.github.joaoVitorLeal.marketsphere.orders.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.joaoVitorLeal.marketsphere.orders.exception.MessagingDeserializationException;
import io.github.joaoVitorLeal.marketsphere.orders.service.OrderLifecycleService;
import io.github.joaoVitorLeal.marketsphere.orders.subscriber.event.OrderBilledEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderLifecycleListener {

    private final OrderLifecycleService orderLifecycleService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${market-sphere.config.kafka.topics.billed-orders}"
    )
    public void handleOrderBilled(String messageJson) {
        log.info("Receive OrderBilledEvent JSON: {}.", messageJson);
        try {
            OrderBilledEvent orderBilledEvent =  objectMapper.readValue(messageJson, OrderBilledEvent.class);
            orderLifecycleService.processOrderBilled(orderBilledEvent);
            log.info("Order successfully updated.");
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize OrderBilledEvent JSON. Message might be a poison pill. Payload: {}. Error: {}",
                    messageJson,
                    e.getMessage(),
                    e
            );
            throw new MessagingDeserializationException("Failed to deserialize Kafka message", e);
        }
    }
}
