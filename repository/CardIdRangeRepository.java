package com.hbi.oms.digitalloyaltycard.domain.repository;

import com.hbi.oms.digitalloyaltycard.domain.entity.CardIdRange;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CardIdRangeRepository extends CrudRepository<CardIdRange, UUID>, CardIdRangeRepositoryCustom {
}
