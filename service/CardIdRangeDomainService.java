package com.hbi.oms.digitalloyaltycard.domain.service;

import com.hbi.oms.digitalloyaltycard.component.EAN13CheckDigitAppender;
import com.hbi.oms.digitalloyaltycard.domain.entity.CardIdRange;
import com.hbi.oms.digitalloyaltycard.domain.exception.UnsupportedCountryException;
import com.hbi.oms.digitalloyaltycard.domain.repository.CardIdRangeRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CardIdRangeDomainService {

    private final CardIdRangeRepository cardIdRangeRepository;
    private final EAN13CheckDigitAppender ean13CheckDigitAppender;

    /**
     * Generate the next card ID for the given country.
     * <p>
     * N.B. that due to Siebel's pre-allocated IDs in the database, the returned ID is not guaranteed to not already to be assigned
     * to a LoyaltyCardAllocation in the database. This is because the underlying CardIdRanges does not know about these pre-allocations.
     * The caller is responsible for checking that the ID returned by this method is not in use elsewhere in the database.
     *
     * <p>
     * This method is called in a new transaction to ensure the underlying CardIdRange is always incremented, even if the transaction of
     * the calling method is rolled back.
     *
     * @param country The country get the next card id for
     * @return The next ID in the range for the given country. N.B. it's not guaranteed for this card-id to not already be
     * in use by a different LoyaltyCardAllocation as there are some random pre-existing IDs in the database already
     * from Siebel.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateNextIdForCountry(String country) {
        CardIdRange range = cardIdRangeRepository.findByCountryAndIncrementCurrentValue(country)
            .orElseThrow(() -> new UnsupportedCountryException(country));

        String currentId = String.valueOf(range.getRangeStart() + range.getCurrentValue());
        int requiredPadding = EAN13CheckDigitAppender.INPUT_LENGTH - range.getPrefix().length();
        String uncheckedId = range.getPrefix() + StringUtils.leftPad(currentId, requiredPadding, "0");
        return ean13CheckDigitAppender.toEAN13CheckedNumber(uncheckedId);
    }
}
