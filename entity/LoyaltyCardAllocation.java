package com.hbi.oms.digitalloyaltycard.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "loyalty_card", schema = "digital_loyalty_card")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class LoyaltyCardAllocation {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "card_id", nullable = false, unique = true)
    private String cardId;

    @Column(name = "country", nullable = false)
    private String country;

    @OneToOne
    @JoinColumn(name = "customer_id", unique = true, referencedColumnName = "id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AllocationStatus status;

    @Column(name = "allocation_attempts", nullable = false)
    private Integer allocationAttempts;

}
