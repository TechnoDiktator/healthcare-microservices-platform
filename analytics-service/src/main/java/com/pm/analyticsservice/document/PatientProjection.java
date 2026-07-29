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
@Document(collection = "patients")
public class PatientProjection {

    @Id
    private String patientId;

    private String name;

    private String email;

    private String address;

    private String dateOfBirth;

    private String registeredDate;

    private String eventType;

    private Long occurredAt;
}