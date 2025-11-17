package io.github.joaoVitorLeal.marketsphere.orders.mapper;

import io.github.joaoVitorLeal.marketsphere.orders.dto.OrderItemRequestDto;
import io.github.joaoVitorLeal.marketsphere.orders.mapper.provider.ProductPriceProvider;
import io.github.joaoVitorLeal.marketsphere.orders.model.OrderItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = { ProductPriceProvider.class },
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "unitPrice", source = "productId", qualifiedByName = "fetchProductUnitPrice")
    OrderItem toOrderItemEntity(OrderItemRequestDto orderItemRequestDto);
}
