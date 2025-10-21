package io.github.joaoVitorLeal.marketsphere.orders.mapper;

import io.github.joaoVitorLeal.marketsphere.orders.client.customers.representation.CustomerRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderDetailsResponseDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderItemDetailsResponseDto;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "order.orderDate", target = "orderDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "order.total", target = "orderTotal")
    @Mapping(source = "order.status", target = "orderStatus")
    @Mapping(source = "order.observations", target = "orderObservations")
    @Mapping(source = "customerRepresentation", target = "customer")
    @Mapping(source = "orderItemDtos", target = "orderItems")
    OrderDetailsResponseDto toOrderDetailsDto(
            Order order,
            CustomerRepresentation customerRepresentation,
            List<OrderItemDetailsResponseDto> orderItemDtos
    );
}
