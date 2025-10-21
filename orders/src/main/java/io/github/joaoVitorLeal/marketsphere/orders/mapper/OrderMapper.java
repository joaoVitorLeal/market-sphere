package io.github.joaoVitorLeal.marketsphere.orders.mapper;

import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderResponseDto;
import io.github.joaoVitorLeal.marketsphere.orders.model.Order;
import io.github.joaoVitorLeal.marketsphere.orders.model.enums.OrderStatus;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.time.Instant;

@Mapper(
        componentModel = "spring",
        uses = { OrderItemMapper.class }
)
public interface OrderMapper {

//    OrderItemMapper ORDER_ITEM_MAPPER = Mappers.getMapper(OrderItemMapper.class);

//    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "toOrderItemEntities")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "paymentKey", ignore = true)
    @Mapping(target = "observations", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "trackingCode", ignore = true)
    @Mapping(target = "invoiceUrl", ignore = true)
    Order toOrderEntity(OrderRequestDto orderRequestDto);

    @Mapping(target = "amountItems", expression = "java(order.getOrderItems() != null ? order.getOrderItems().size() : 0)")
    OrderResponseDto toOrderDto(Order order);

//    @Named("toOrderItemEntities")
//    default List<OrderItem> toOrderItemEntities(List<OrderItemRequestDto> orderItemRequestDtos) {
//        return orderItemRequestDtos.stream()
//                .map(ORDER_ITEM_MAPPER::toOrderItemEntity)
//                .toList();
//    }

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
