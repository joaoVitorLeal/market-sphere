package io.github.joaoVitorLeal.marketsphere.billing.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.joaoVitorLeal.marketsphere.billing.exception.OrderProcessingException;
import io.github.joaoVitorLeal.marketsphere.billing.mapper.OrderMapper;
import io.github.joaoVitorLeal.marketsphere.billing.model.Order;
import io.github.joaoVitorLeal.marketsphere.billing.service.InvoiceGeneratorService;
import io.github.joaoVitorLeal.marketsphere.billing.subscriber.event.PaidOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaidOrderSubscriber {

    private final ObjectMapper objectMapper;
    private final OrderMapper orderMapper;
    private final InvoiceGeneratorService invoiceService;


    public PaidOrderSubscriber(ObjectMapper objectMapper, OrderMapper orderMapper, InvoiceGeneratorService invoiceService) {
        this.objectMapper = objectMapper;
        this.orderMapper = orderMapper;
        this.invoiceService = invoiceService;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${market-sphere.config.kafka.topics.paid-orders}"
    )
    public void listen(String jsonContent) {
        PaidOrderEvent paidOrderEvent = null;
        try {
            log.info("Receiving order for billing: {}", jsonContent);

            // Converter o JSON enviado pelo producer para PaidOrderEvent
            paidOrderEvent = objectMapper.readValue(jsonContent, PaidOrderEvent.class);
            Order order = orderMapper.toDomainModel(paidOrderEvent);
            invoiceService.generate(order);

        } catch (JsonProcessingException e) {
            log.error("Invalid JSON received on paid-orders topic. Cannot process message. Payload: {}", jsonContent, e);
            throw new OrderProcessingException("Malformed JSON received, message is a poison pill.", e);

        } catch (OrderProcessingException e) {
            assert paidOrderEvent != null;
            log.error("Failed to process order for billing for Order ID: {}. Reason: {}",
                    paidOrderEvent.orderId(), e.getMessage(), e);            // Este catch agora pega as falhas de geração/upload encapsuladas pelo InvoiceGeneratorService
            throw e;
        }
    }
}
