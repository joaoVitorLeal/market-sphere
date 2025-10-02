package io.github.joaoVitorLeal.marketsphere.customers.controller;

import io.github.joaoVitorLeal.marketsphere.customers.controller.util.HeaderLocationBuilder;
import io.github.joaoVitorLeal.marketsphere.customers.dto.CustomerRequestDto;
import io.github.joaoVitorLeal.marketsphere.customers.dto.CustomerResponseDto;
import io.github.joaoVitorLeal.marketsphere.customers.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
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
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long customerId) {
        return ResponseEntity.ok(service.getCustomerById(customerId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        return ResponseEntity.ok(service.getAllCustomers());
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(Long customerId) {
        service.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }
}
