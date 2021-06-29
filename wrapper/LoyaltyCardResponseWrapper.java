package com.hbi.oms.digitalloyaltycard.domain.wrapper;

import com.hbi.oms.digitalloyaltycard.dto.LoyaltyCardResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class LoyaltyCardResponseWrapper {
    private final LoyaltyCardResponseDTO loyaltyCardResponseDTO;
    private final boolean newAllocation;
}
