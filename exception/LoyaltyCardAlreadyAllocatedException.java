package com.hbi.oms.digitalloyaltycard.domain.exception;

/**
 * An Exception indicating that a new LoyaltyCardAllocation failed because the cardId was already allocated.
 */
public class LoyaltyCardAlreadyAllocatedException extends Exception {

    public LoyaltyCardAlreadyAllocatedException(String message) {
        super(message);
    }

    public LoyaltyCardAlreadyAllocatedException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
