package io.github.joaoVitorLeal.marketsphere.products.service.impl;

import io.github.joaoVitorLeal.marketsphere.products.exception.ProductNotFoundException;
import io.github.joaoVitorLeal.marketsphere.products.dto.ProductRequestDto;
import io.github.joaoVitorLeal.marketsphere.products.dto.ProductResponseDto;
import io.github.joaoVitorLeal.marketsphere.products.model.Product;
import io.github.joaoVitorLeal.marketsphere.products.repository.ProductRepository;
import io.github.joaoVitorLeal.marketsphere.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Transactional
    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = new Product(productRequestDto.name(), productRequestDto.unitPrice(), productRequestDto.description());
        repository.save(product);
        return new ProductResponseDto(product.getId(), product.getName(), product.getUnitPrice(), product.getDescription());
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponseDto getProductById(Long productId) {
        Product product = repository.findById(productId)
                .orElseThrow( ()-> new ProductNotFoundException(productId) );
        return new ProductResponseDto(product.getId(), product.getName(), product.getUnitPrice(), product.getDescription());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponseDto> getAllProducts() {
        return repository.findAll()
                .stream()
                .map( product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getUnitPrice(),
                        product.getDescription()
                ))
                .toList();
    }
}
