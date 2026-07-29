package com.pm.analyticsservice.repository;

import com.pm.analyticsservice.document.BillingProjection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingProjectionRepository
        extends MongoRepository<BillingProjection, String> {
}