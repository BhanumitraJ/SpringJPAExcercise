package com.hbi.oms.digitalloyaltycard.domain.service;

import com.hbi.oms.digitalloyaltycard.component.mapper.CustomerMapper;
import com.hbi.oms.digitalloyaltycard.domain.entity.Customer;
import com.hbi.oms.digitalloyaltycard.domain.repository.CustomerRepository;
import com.hbi.oms.digitalloyaltycard.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerDomainService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    /**
     * Gets the customer entity from the database based on the CustomerIdentifier of the given DTO. If it does not exist,
     * the given DTO is persisted to the database instead, and then returned. N.B if the customer already exists
     * then none of its fields are updated.
     *
     * @param customerDto DTO object to get/create the customer from
     * @return The Customer entity from the (or persisted to) the database
     */
    public Customer findOrCreateCustomer(CustomerDTO customerDto) {
        Customer customer = customerMapper.fromCustomerDTO(customerDto);
        Optional<Customer> customerOptional = customerRepository.findCustomerByCustomerIdentifier_Identifier(customer.getCustomerIdentifier().getIdentifier());
        return customerOptional.orElseGet(() -> customerRepository.save(customer));
    }

}
