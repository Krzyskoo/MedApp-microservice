package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.constants.AppointmentStatus;
import com.example.appointmentservice.dto.AppointmentRequestDto;
import com.example.appointmentservice.dto.PaymentFinalizationEventDTO;
import com.example.appointmentservice.dto.PaymentRequestDto;
import com.example.appointmentservice.dto.UserRequestDto;
import com.example.appointmentservice.model.Appointment;
import com.example.appointmentservice.repo.AppointmentRepo;
import com.example.appointmentservice.service.IAppointmentService;
import com.example.appointmentservice.service.client.PaymentFeignClient;
import com.example.appointmentservice.service.client.UserFeignClient;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final UserFeignClient userFeignClient;
    private final PaymentFeignClient paymentFeignClient;
    private final DoctorScheduleServiceImpl doctorScheduleService;

    public AppointmentServiceImpl(AppointmentRepo appointmentRepo,
                                  UserFeignClient userFeignClient,
                                  PaymentFeignClient paymentFeignClient,
                                  DoctorScheduleServiceImpl doctorScheduleService) {
        this.appointmentRepo = appointmentRepo;
        this.userFeignClient = userFeignClient;
        this.paymentFeignClient = paymentFeignClient;
        this.doctorScheduleService = doctorScheduleService;

    }

    @Override
    public String createAppointment(AppointmentRequestDto appointmentRequestDto) {
        UserRequestDto userRequestDto = userFeignClient.getCurrentUserIdAndEmail();

        Appointment appointment = Appointment.builder()
                .doctorId(appointmentRequestDto.getDoctorId())
                .startTime(appointmentRequestDto.getStartAppointment())
                .endTime(appointmentRequestDto.getStartAppointment().plusMinutes(30))
                .notes(appointmentRequestDto.getNotes())
                .patientId(userRequestDto.getUserId())
                .status(AppointmentStatus.CREATED)
                .build();

        appointmentRepo.save(appointment);
        return paymentFeignClient.createCheckoutSession(new PaymentRequestDto(
                appointmentRequestDto.getAmount(),
                "PLN",
                appointment.getId(),
                userRequestDto.getEmail()
        ));


    }

    @Override
    public void markPaymentAsFinalized(PaymentFinalizationEventDTO paymentFinalizationEventDTO) {
        Appointment appointment = appointmentRepo.findById(paymentFinalizationEventDTO.getAppointmentId()).get();
        if (appointment==null) {
            throw new RuntimeException("Appointment not found");
        }
        switch (paymentFinalizationEventDTO.getPaymentStatus()) {
            case SUCCEEDED -> appointment.setStatus(AppointmentStatus.CONFIRMED);
            case FAILED -> appointment.setStatus(AppointmentStatus.CANCELLED);
        }
        appointmentRepo.save(appointment);

    }
}
