package io.github.joaoVitorLeal.marketsphere.customers.service.impl;

import io.github.joaoVitorLeal.marketsphere.customers.client.BrasilApi;
import io.github.joaoVitorLeal.marketsphere.customers.dto.CustomerRequestDto;
import io.github.joaoVitorLeal.marketsphere.customers.dto.CustomerResponseDto;
import io.github.joaoVitorLeal.marketsphere.customers.dto.brasilapi.BrasilApiAddressDto;
import io.github.joaoVitorLeal.marketsphere.customers.exception.CustomerNotFoundException;
import io.github.joaoVitorLeal.marketsphere.customers.mapper.CustomerMapper;
import io.github.joaoVitorLeal.marketsphere.customers.model.Customer;
import io.github.joaoVitorLeal.marketsphere.customers.repository.CustomerRepository;
import io.github.joaoVitorLeal.marketsphere.customers.service.CustomerService;
import io.github.joaoVitorLeal.marketsphere.customers.validator.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final BrasilApi brasilApiClient;
    private final CustomerMapper mapper;
    private final CustomerValidator validator;

    @Transactional
    @Override
    public CustomerResponseDto createCustomer(final CustomerRequestDto customerRequestDto) {
        validator.validateForCreate(customerRequestDto);
        BrasilApiAddressDto brasilApiAddressDto = brasilApiClient.getAddressDataByPostalCode(customerRequestDto.postalCode());
        return mapper.toCustomerDto(
                repository.save(mapper.toCustomerEntity(customerRequestDto, brasilApiAddressDto))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponseDto getCustomerById(final Long customerId) {
        return repository.findById(customerId)
                .map(mapper::toCustomerDto)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::toCustomerDto)
                .toList();
    }

    @Transactional
    @Override
    public void updateCustomer(final Long customerId, final CustomerRequestDto customerRequestDto) {
        Customer customerToUpdate = repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        validator.validateForUpdate(customerToUpdate, customerRequestDto);
        // valida postalCode chamando Brasil API
        BrasilApiAddressDto brasilApiAddressDto = brasilApiClient.getAddressDataByPostalCode(customerRequestDto.postalCode());
        mapper.updateCustomerEntity(customerToUpdate, customerRequestDto, brasilApiAddressDto);
    }

    @Transactional
    @Override
    public void deleteCustomerById(final Long customerId) {
        if (!repository.existsById(customerId)) {
            throw new  CustomerNotFoundException(customerId);
        }
        repository.deleteById(customerId);
    }
}
