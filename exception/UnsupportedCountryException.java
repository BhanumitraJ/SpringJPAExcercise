package com.hbi.oms.digitalloyaltycard.domain.exception;

public class UnsupportedCountryException extends BusinessException {

    public UnsupportedCountryException(String country) {
        super("cardIdGeneration.unsupportedCountry.exception", country);
    }

}
