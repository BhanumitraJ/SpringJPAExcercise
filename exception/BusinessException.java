package com.hbi.oms.digitalloyaltycard.domain.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final Object[] messageArgs;

    public BusinessException(String messageKey, Object... args) {
        super(messageKey);
        this.messageArgs = args;
    }

}
