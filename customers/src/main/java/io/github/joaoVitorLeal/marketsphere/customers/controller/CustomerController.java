package io.github.joaoVitorLeal.marketsphere.customers.controller;

import io.github.joaoVitorLeal.marketsphere.customers.controller.util.HeaderLocationBuilder;
import io.github.joaoVitorLeal.marketsphere.customers.dto.CustomerRequestDto;
import io.github.joaoVitorLeal.marketsphere.customers.dto.CustomerResponseDto;
import io.github.joaoVitorLeal.marketsphere.customers.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCustomer(@RequestBody @Valid CustomerRequestDto customerRequestDto) {
        CustomerResponseDto customerResponseDto = service.createCustomer(customerRequestDto);
        return ResponseEntity
                .created(HeaderLocationBuilder.build(customerResponseDto.id()))
                .build();
    }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDto> getCustomerById(
            @PathVariable @Positive(message = "{customer.id.positive}") Long customerId
    ) {
        return ResponseEntity.ok(service.getCustomerById(customerId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<CustomerResponseDto> costumers = service.getAllCustomers();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(costumers.size()))
                .body(costumers);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(
            @PathVariable @Positive(message = "{customer.id.positive}") Long customerId
    ) {
        service.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCustomer(
            @PathVariable @Positive(message = "{customer.id.positive}") Long customerId,
            @RequestBody @Valid CustomerRequestDto customerRequestDto
    ) {
        service.updateCustomer(customerId, customerRequestDto);
        return ResponseEntity.noContent().build();
    }
}
