package io.github.joaoVitorLeal.marketsphere.orders.publisher.mapper;

import io.github.joaoVitorLeal.marketsphere.orders.client.products.representation.ProductRepresentation;
import io.github.joaoVitorLeal.marketsphere.orders.model.OrderItem;
import io.github.joaoVitorLeal.marketsphere.orders.publisher.representation.OrderItemRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemRepresentationMapper {

    @Mapping(source = "orderItem.productId", target = "productId")
    @Mapping(source = "orderItem.amount", target = "amount")
    @Mapping(source = "orderItem.unitPrice", target = "unitPrice")
    @Mapping(source = "productRepresentation.name", target = "productName")
    OrderItemRepresentation toOrderItemRepresentation(OrderItem orderItem, ProductRepresentation productRepresentation);
}
