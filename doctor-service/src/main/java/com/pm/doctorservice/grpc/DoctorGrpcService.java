package com.pm.doctorservice.grpc;

import com.pm.doctorservice.dto.DoctorResponseDTO;
import com.pm.doctorservice.enums.Specialization;
import com.pm.doctorservice.service.DoctorService;
import doctor.DoctorListResponse;
import doctor.DoctorResponse;
import doctor.DoctorSpecializationRequest;
import doctor.DoctorServicesGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class DoctorGrpcService extends DoctorServicesGrpc.DoctorServicesImplBase {

    private final DoctorService doctorService;

    public DoctorGrpcService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public void getDoctorsBySpecialization(
            DoctorSpecializationRequest request,
            StreamObserver<DoctorListResponse> responseObserver) {



        Specialization specialization =
                Specialization.valueOf(
                        request.getSpecialization().name()
                );

        List<DoctorResponseDTO> doctors =
                doctorService.getDoctorsBySpecialization(specialization);

        List<DoctorResponse> protoDoctors = doctors.stream()
                .map(this::toProtoDoctor)
                .toList();

        DoctorListResponse response = DoctorListResponse.newBuilder()
                .addAllDoctors(protoDoctors)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private DoctorResponse toProtoDoctor(DoctorResponseDTO dto) {

        return DoctorResponse.newBuilder()
                .setId(dto.getId())
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setEmail(dto.getEmail())
                .setSpecialization(dto.getSpecialization().name())
                .setPhoneNumber(dto.getPhoneNumber())
                .setQualification(dto.getQualification())
                .setExperience(dto.getExperience())
                .build();
    }
}