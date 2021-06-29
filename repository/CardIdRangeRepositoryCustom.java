package com.hbi.oms.digitalloyaltycard.domain.repository;

import com.hbi.oms.digitalloyaltycard.domain.entity.CardIdRange;

import java.util.Optional;

public interface CardIdRangeRepositoryCustom {

    /**
     * Atomically increment the currentValue for CardIdRange associated with the given country. This method uses a pessimistic write
     * lock to ensure the counter is incremented correctly.
     *
     * If the increment would cause the currentValue to exceed the range, a CardIdRangeOutOfBoundsException is thrown.
     *
     * @param country The country to return the CardIdRange for
     * @return The CardIdRange, but with its current value incremented by 1. If there is no CardIdRange
     * for the given country then an empty Optional is returned
     */
    Optional<CardIdRange> findByCountryAndIncrementCurrentValue(String country);

}
