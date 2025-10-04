package io.github.joaoVitorLeal.marketsphere.orders.mapper;

import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderItemDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderResponseDto;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import io.github.joaoVitorLeal.marketsphere.orders.model.OrderItem;
import io.github.joaoVitorLeal.marketsphere.orders.model.enums.OrderStatus;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderItemMapper ORDER_ITEM_MAPPER = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "toOrderItemsEntities")
    Order toOrderEntity(OrderRequestDto orderRequestDto);

    OrderResponseDto toOrderDto(Order order);

    @Named("toOrderItemsEntities")
    default List<OrderItem> toOrderItemEntities(List<OrderItemDto> orderItemDtos) {
        return orderItemDtos.stream()
                .map(ORDER_ITEM_MAPPER::toOrderItemEntity)
                .toList();
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Order order) {
        order.setStatus(OrderStatus.PLACED);
        order.setOrderDate(Instant.now());
        var total = calculateTotalPrice(order);
        order.setTotal(total);
        order.getOrderItems().forEach(item -> item.setOrder(order));
    }

    private static BigDecimal calculateTotalPrice(Order order) {
        return order.getOrderItems()
                .stream()
                .map(item ->
                        item.getUnitPrice().multiply(BigDecimal.valueOf(item.getAmount()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .abs();
    }
}
