package com.pm.analyticsservice.repository;

import com.pm.analyticsservice.document.DoctorProjection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorProjectionRepository
        extends MongoRepository<DoctorProjection, String> {
}