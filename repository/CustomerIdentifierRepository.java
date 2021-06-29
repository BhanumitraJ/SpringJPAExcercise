package com.hbi.oms.digitalloyaltycard.domain.repository;

import com.hbi.oms.digitalloyaltycard.domain.entity.CustomerIdentifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerIdentifierRepository extends CrudRepository<CustomerIdentifier, UUID> {
}
