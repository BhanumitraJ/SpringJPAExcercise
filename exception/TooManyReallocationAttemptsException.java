package com.hbi.oms.digitalloyaltycard.domain.exception;

public class TooManyReallocationAttemptsException extends RuntimeException {

    public TooManyReallocationAttemptsException(String msg) {
        super(msg);
    }

}
