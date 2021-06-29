package com.hbi.oms.digitalloyaltycard.domain.entity;

import com.hbi.oms.digitalloyaltycard.dto.CustomerIdentifierType;
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
import javax.persistence.Table;

@Entity
@Table(name = "customer_identifier", schema = "digital_loyalty_card")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CustomerIdentifier {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CustomerIdentifierType type;

}
