package com.pm.analyticsservice.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "prescriptions")
public class PrescriptionProjection {

    @Id
    private String prescriptionId;

    private String patientId;

    private String doctorId;

    private String diagnosis;

    private List<String> medicines;

    private Double consultationFee;

    private String prescribedAt;

    private String notes;

    private String billId;

    private String eventType;

    private Long occurredAt;
}