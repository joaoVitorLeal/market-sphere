package io.github.joaoVitorLeal.marketsphere.customers.mapper;

import io.github.joaoVitorLeal.marketsphere.customers.dto.CustomerRequestDto;
import io.github.joaoVitorLeal.marketsphere.customers.dto.CustomerResponseDto;
import io.github.joaoVitorLeal.marketsphere.customers.dto.brasilapi.BrasilApiAddressDto;
import io.github.joaoVitorLeal.marketsphere.customers.model.Customer;
import io.github.joaoVitorLeal.marketsphere.customers.model.vo.Address;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerMapper {

    public Customer toCustomerEntity(
            final CustomerRequestDto customerRequestDto,
            final BrasilApiAddressDto brasilApiAddressDto
    ) {
        Objects.requireNonNull(customerRequestDto, "CustomerRequestDto must not be null.");
        Objects.requireNonNull(brasilApiAddressDto, "BrasilApiAddressDto must not be null.");

        Customer customer = new Customer();
        customer.setFullName(customerRequestDto.fullName());
        customer.setNationalId(customerRequestDto.nationalId());
        customer.setEmail(customerRequestDto.email());
        customer.setPhoneNumber(customerRequestDto.phoneNumber());
        customer.setAddressVo(
                this.adaptAddress(
                        brasilApiAddressDto,
                        customerRequestDto.number(),
                        customerRequestDto.complement(),
                        customerRequestDto.country()
                )
        );
        return customer;
    }

    public CustomerResponseDto toCustomerDto(final Customer customer) {
        Objects.requireNonNull(customer, "Customer must not be null");
        Address addressVo = customer.getAddressVo();
        Objects.requireNonNull(addressVo, "Address must not be null");

        return new CustomerResponseDto(
                customer.getId(),
                customer.getFullName(),
                customer.getNationalId(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                addressVo.getPostalCode(),
                addressVo.getStreet(),
                addressVo.getNumber(),
                addressVo.getComplement(),  // Pode ser nulo
                addressVo.getNeighborhood(), // Pode ser nulo
                addressVo.getCity(),
                addressVo.getState(),
                addressVo.getCountry()
        );
    }

    public void updateCustomerEntity(
            final Customer customerToUpdate,
            final CustomerRequestDto customerRequestDto,
            final BrasilApiAddressDto brasilApiAddressDto
    ) {
        Objects.requireNonNull(customerToUpdate, "Customer must not be null.");
        Objects.requireNonNull(customerRequestDto, "CustomerRequestDto must not be null.");
        Objects.requireNonNull(brasilApiAddressDto, "BrasilApiAddressDto must not be null.");

        customerToUpdate.setFullName(customerRequestDto.fullName());
        customerToUpdate.setNationalId(customerRequestDto.nationalId());
        customerToUpdate.setEmail(customerRequestDto.email());
        customerToUpdate.setPhoneNumber(customerRequestDto.phoneNumber());
        customerToUpdate.setAddressVo(
                this.adaptAddress(
                        brasilApiAddressDto,
                        customerRequestDto.number(),
                        customerRequestDto.complement(),
                        customerRequestDto.country()
                )
        );
    }

    private Address adaptAddress(
            final BrasilApiAddressDto brasilApiAddressDto,
            final String number,
            final String complement,
            final String country
    ) {
         return new Address(
                 brasilApiAddressDto.postalCode(),
                 brasilApiAddressDto.street(),
                 number,
                 complement,
                 brasilApiAddressDto.neighborhood(),
                 brasilApiAddressDto.city(),
                 brasilApiAddressDto.state(),
                 country
         );
    }
}
