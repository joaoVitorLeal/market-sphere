package io.github.joaoVitorLeal.marketsphere.orders.publisher.mapper;

import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.representation.OrderItemRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.representation.OrderRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderRepresentationMapper {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "order.orderDate", target = "orderDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "order.total", target = "orderTotal")
    @Mapping(source = "order.status", target = "orderStatus")
    @Mapping(source = "order.observations", target = "orderObservations")
    @Mapping(source = "customerRepresentation", target = "customer")
    @Mapping(source = "orderItemRepresentations", target = "orderItems")
    OrderRepresentation toOrderRepresentation(
            Order order,
            CustomerRepresentation customerRepresentation,
            List<OrderItemRepresentation> orderItemRepresentations
    );
}
