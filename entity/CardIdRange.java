package com.hbi.oms.digitalloyaltycard.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "card_id_range", schema = "digital_loyalty_card")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CardIdRange {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "country", unique = true, nullable = false)
    private String country;

    @Column(name = "prefix", nullable = false)
    private String prefix;

    @Column(name = "range_start", nullable = false)
    private Long rangeStart;

    @Column(name = "range_end", nullable = false)
    private Long rangeEnd;

    @Column(name = "current_value", nullable = false)
    private Long currentValue;

}
