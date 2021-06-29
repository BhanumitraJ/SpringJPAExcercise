package com.hbi.oms.digitalloyaltycard.domain.repository;

import com.hbi.oms.digitalloyaltycard.domain.entity.CardIdRange;
import com.hbi.oms.digitalloyaltycard.domain.exception.CardIdRangeOutOfBoundsException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@AllArgsConstructor(access = AccessLevel.PACKAGE) // For unit tests
public class CardIdRangeRepositoryCustomImpl implements CardIdRangeRepositoryCustom {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<CardIdRange> findByCountryAndIncrementCurrentValue(String country) {
        TypedQuery<CardIdRange> query = em.createQuery("SELECT c FROM CardIdRange c WHERE c.country = :country", CardIdRange.class);
        query.setParameter("country", country);
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);

        Optional<CardIdRange> idRangeOption = query.getResultList().stream().findFirst();

        idRangeOption.ifPresent(idRange -> {
            idRange.setCurrentValue(idRange.getCurrentValue() + 1);
            if (idRange.getCurrentValue() + idRange.getRangeStart() > idRange.getRangeEnd()) {
                throw new CardIdRangeOutOfBoundsException(country);
            }
        });


        return idRangeOption;
    }
}
