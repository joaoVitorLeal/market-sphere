package io.github.joaoVitorLeal.marketsphere.products.service;

import io.github.joaoVitorLeal.marketsphere.products.dto.ProductRequestDto;
import io.github.joaoVitorLeal.marketsphere.products.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);
    ProductResponseDto getProductById(Long productId);
    List<ProductResponseDto> getAllProducts();
    List<ProductResponseDto> getAllProductsByIds(List<Long> productsIds);
}
