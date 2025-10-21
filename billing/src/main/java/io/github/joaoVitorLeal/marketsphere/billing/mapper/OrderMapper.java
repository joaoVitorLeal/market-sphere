package io.github.joaoVitorLeal.marketsphere.billing.mapper;

import io.github.joaoVitorLeal.marketsphere.billing.model.Address;
import io.github.joaoVitorLeal.marketsphere.billing.model.Customer;
import io.github.joaoVitorLeal.marketsphere.billing.model.Order;
import io.github.joaoVitorLeal.marketsphere.billing.model.OrderItem;
import io.github.joaoVitorLeal.marketsphere.billing.subscriber.event.PaidOrderEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    /**
     * Converte a representação de um pedido completo (vinda do Kafka)
     * para o modelo de domínio interno.
     *
     * @param paidOrderEvent representação do pedido recebida via Kafka
     * @return Order - objeto de domínio correspondente
     */
    public Order toDomainModel(PaidOrderEvent paidOrderEvent) {
        Customer customer = new Customer(
                paidOrderEvent.customer().fullName(),
                paidOrderEvent.customer().nationalId(),
                paidOrderEvent.customer().email(),
                paidOrderEvent.customer().phoneNumber(),
                new Address(
                        paidOrderEvent.customer().postalCode(),
                        paidOrderEvent.customer().street(),
                        paidOrderEvent.customer().number(),
                        paidOrderEvent.customer().complement(),
                        paidOrderEvent.customer().neighborhood(),
                        paidOrderEvent.customer().city(),
                        paidOrderEvent.customer().state(),
                        paidOrderEvent.customer().country()
                )
        );

        List<OrderItem> orderItems = paidOrderEvent.orderItems()
                .stream()
                .map(orderItemMapper::toDomainModel)
                .toList();

        return new Order(
                paidOrderEvent.orderId(),
                paidOrderEvent.orderDate(),
                paidOrderEvent.orderObservations(),
                customer,
                orderItems,
                paidOrderEvent.orderTotal()
        );
    }
}
