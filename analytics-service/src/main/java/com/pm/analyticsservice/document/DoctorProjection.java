package com.pm.analyticsservice.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "doctors")
public class DoctorProjection {

    @Id
    private String doctorId;

    private String firstName;

    private String lastName;

    private String email;

    private String specialization;

    private String phoneNumber;

    private String qualification;

    private Integer experience;

    private Boolean available;

    private String eventType;

    private Long occurredAt;
}