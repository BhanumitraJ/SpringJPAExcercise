package com.hbi.oms.digitalloyaltycard.domain.exception;

/**
 * An Exception indicating that the currentValue of a CardIdRange has been exceeded. This shouldn't ever happen as
 * the ranges are quite large, so the most likely cause is the ranges being input incorrectly to the database. In any
 * case, this exception will require some manual investigation to fix.
 */
public class CardIdRangeOutOfBoundsException extends BusinessException {

    public CardIdRangeOutOfBoundsException(String country) {
        super("cardIdGeneration.rangeExceeded.exception", country);
    }

}
