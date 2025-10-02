package io.github.joaoVitorLeal.marketsphere.customers.service;

import io.github.joaoVitorLeal.marketsphere.customers.dto.CustomerRequestDto;
import io.github.joaoVitorLeal.marketsphere.customers.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {

    CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto);
    CustomerResponseDto getCustomerById(Long id);
    List<CustomerResponseDto> getAllCustomers();
    void updateCustomer(Long id, CustomerRequestDto customerRequestDto);
    void deleteCustomerById(Long id);
}
