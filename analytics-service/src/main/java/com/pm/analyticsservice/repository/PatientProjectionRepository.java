package com.pm.analyticsservice.repository;

import com.pm.analyticsservice.document.PatientProjection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientProjectionRepository
        extends MongoRepository<PatientProjection, String> {
}