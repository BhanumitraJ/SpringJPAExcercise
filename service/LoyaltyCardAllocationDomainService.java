package com.hbi.oms.digitalloyaltycard.domain.service;

import com.hbi.oms.digitalloyaltycard.domain.entity.AllocationStatus;
import com.hbi.oms.digitalloyaltycard.domain.entity.Customer;
import com.hbi.oms.digitalloyaltycard.domain.entity.LoyaltyCardAllocation;
import com.hbi.oms.digitalloyaltycard.domain.exception.LoyaltyCardAlreadyAllocatedException;
import com.hbi.oms.digitalloyaltycard.domain.repository.LoyaltyCardAllocationRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class LoyaltyCardAllocationDomainService {

    private static final String POSTGRES_UNIQUE_CONSTRAINT_VIOLATION_CODE = "23505";

    private final LoyaltyCardAllocationRepository loyaltyCardAllocationRepository;

    /**
     * Allocates the given card ID to the given customer.
     *
     * @param country  Country this allocation is for
     * @param customer The Customer to allocate the card to
     * @param cardId   The ID of the card
     * @return The LoyaltyCardAllocation entity persisted to the database
     * @throws LoyaltyCardAlreadyAllocatedException If the cardId is already in use
     */
    public LoyaltyCardAllocation allocate(String country, Customer customer, String cardId) throws LoyaltyCardAlreadyAllocatedException {
        LoyaltyCardAllocation allocation = LoyaltyCardAllocation.builder()
            .cardId(cardId)
            .country(country)
            .customer(customer)
            .status(AllocationStatus.PENDING_ALLOCATION)
            .allocationAttempts(0)
            .build();
        try {
            return loyaltyCardAllocationRepository.saveAndFlush(allocation);
        } catch (DataIntegrityViolationException e) {
            if (isUniqueConstraintViolation(e)) {
                throw new LoyaltyCardAlreadyAllocatedException("Allocation already exists for cardId: " + cardId, e);
            }
            throw e;
        }
    }

    /**
     * Re-allocates the given LoyaltyCardAllocation a new card id, sets the status to PENDING_ALLOCATION and sets the
     * current allocationAttempt to the given value. The caller is responsible for checking if the allocationAttempt
     * has been exceeded before calling this method.
     *
     * @param allocationId      ID of a pre-existing allocation in the database
     * @param newCardId         The new card id to assign to it
     * @param allocationAttempt The current allocation attempt for this LoyaltyCardAllocation
     * @return The LoyaltyCardAllocation entity persisted to the database
     * @throws LoyaltyCardAlreadyAllocatedException If the newCardId is already in use
     * @throws IllegalArgumentException             If there is no allocation with the given ID in the database
     */
    public LoyaltyCardAllocation reAllocate(UUID allocationId, String newCardId, int allocationAttempt) throws LoyaltyCardAlreadyAllocatedException {
        LoyaltyCardAllocation allocation = loyaltyCardAllocationRepository.findById(allocationId)
            .orElseThrow(() -> new IllegalArgumentException("No allocation found with id " + allocationId));
        allocation.setCardId(newCardId);
        allocation.setAllocationAttempts(allocationAttempt);
        allocation.setStatus(AllocationStatus.PENDING_ALLOCATION);

        try {
            return loyaltyCardAllocationRepository.saveAndFlush(allocation);
        } catch (DataIntegrityViolationException e) {
            if (isUniqueConstraintViolation(e)) {
                throw new LoyaltyCardAlreadyAllocatedException("Allocation already exists for cardId: " + newCardId, e);
            }
            throw e;
        }
    }

    /**
     * Updates the status of the LoyaltyCardAllocation with the given Card Id
     *
     * @param cardId    Card ID of the allocation to update
     * @param newStatus AllocationStatus to assign to the allocation
     * @return The LoyaltyCardAllocation that has been persisted
     */
    public LoyaltyCardAllocation updateStatus(String cardId, AllocationStatus newStatus) {
        LoyaltyCardAllocation allocation = loyaltyCardAllocationRepository.findByCardId(cardId)
            .orElseThrow(() -> new IllegalArgumentException("No allocation found with cardId " + cardId));
        allocation.setStatus(newStatus);
        return loyaltyCardAllocationRepository.save(allocation);
    }

    private boolean isUniqueConstraintViolation(DataIntegrityViolationException exception) {
        Throwable rootCause = ExceptionUtils.getRootCause(exception);
        if (rootCause instanceof SQLException) {
            return ((SQLException) rootCause).getSQLState().equals(POSTGRES_UNIQUE_CONSTRAINT_VIOLATION_CODE);
        }
        return false;
    }

}
