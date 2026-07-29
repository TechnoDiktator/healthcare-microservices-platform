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
@Document(collection = "billings")
public class BillingProjection {

    @Id
    private String billId;

    private String prescriptionId;

    private String patientId;

    private String doctorId;

    private Double consultationFee;

    private Double medicineCost;

    private Double totalAmount;

    private String paymentStatus;

    private String createdAt;

    private String eventType;

    private Long occurredAt;
}