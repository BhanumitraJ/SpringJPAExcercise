package com.hbi.oms.digitalloyaltycard.domain.repository;

import com.hbi.oms.digitalloyaltycard.domain.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@SuppressWarnings("PMD.MethodNamingConventions")
public interface CustomerRepository extends CrudRepository<Customer, UUID> {

    Optional<Customer> findCustomerByCustomerIdentifier_Identifier(String identifier);

}
