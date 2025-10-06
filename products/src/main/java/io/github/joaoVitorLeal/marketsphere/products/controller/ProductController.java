package io.github.joaoVitorLeal.marketsphere.products.controller;

import io.github.joaoVitorLeal.marketsphere.products.controller.util.HeaderLocationBuilder;
import io.github.joaoVitorLeal.marketsphere.products.dto.ProductRequestDto;
import io.github.joaoVitorLeal.marketsphere.products.dto.ProductResponseDto;
import io.github.joaoVitorLeal.marketsphere.products.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated; // Import necessário
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@Validated // Habilita a validação para @PathVariable e @RequestParam
public class ProductController {

    private final ProductService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = service.createProduct(productRequestDto);
        return ResponseEntity
                .created(HeaderLocationBuilder.build(productResponseDto.id()))
                .build();
    }

    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDto> getProductById(
            @PathVariable @Positive(message = "{product.id.positive}") Long productId
    ) {
        return ResponseEntity.ok(service.getProductById(productId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }
}
