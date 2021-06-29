package com.hbi.oms.digitalloyaltycard.domain.repository;

import com.hbi.oms.digitalloyaltycard.domain.entity.AllocationStatus;
import com.hbi.oms.digitalloyaltycard.domain.entity.Customer;
import com.hbi.oms.digitalloyaltycard.domain.entity.LoyaltyCardAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface LoyaltyCardAllocationRepository extends JpaRepository<LoyaltyCardAllocation, UUID> {

    /**
     * Find the LoyaltyCardAllocation associated with the given Customer, where the status is NOT equal to the given
     * state
     *
     * @param customer The Customer to find the allocation by. N.B this must not be null, as multiple pre-allocated
     *                 cards exist in the database with a null customer, so calling this with null will throw a
     *                 NonUniqueResultException. All customers have exactly 1 non-failed allocation, and any number
     *                 of FAILED allocations.
     * @param status   The AllocationStatus that the result should not be in
     * @return The LoyaltyCardAllocation associated with the Customer, or Empty if there is none
     */
    Optional<LoyaltyCardAllocation> findByCustomerAndStatusIsNot(@NonNull Customer customer, AllocationStatus status);

    Optional<LoyaltyCardAllocation> findByCardId(String cardId);

}
