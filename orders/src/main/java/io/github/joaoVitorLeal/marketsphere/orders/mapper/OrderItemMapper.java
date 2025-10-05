package io.github.joaoVitorLeal.marketsphere.orders.mapper;

import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderItemRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItem toOrderItemEntity(OrderItemRequestDto orderItemRequestDto);
}
