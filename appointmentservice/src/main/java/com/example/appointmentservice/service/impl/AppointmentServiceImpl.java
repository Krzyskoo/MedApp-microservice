package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.config.KafkaProducer;
import com.example.appointmentservice.constants.AppointmentStatus;
import com.example.appointmentservice.dto.*;
import com.example.appointmentservice.exception.AppointmentAlreadyBookedException;
import com.example.appointmentservice.exception.AppointmentNotFoundException;
import com.example.appointmentservice.mapper.AppointmentMapper;
import com.example.appointmentservice.model.Appointment;
import com.example.appointmentservice.model.AppointmentSlot;
import com.example.appointmentservice.repo.AppointmentRepo;
import com.example.appointmentservice.service.IAppointmentService;
import com.example.appointmentservice.service.client.PaymentFeignClient;
import com.example.appointmentservice.service.client.UserFeignClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final UserFeignClient userFeignClient;
    private final PaymentFeignClient paymentFeignClient;
    private final DoctorScheduleServiceImpl doctorScheduleService;
    private final AppointmentSlotServiceImpl appointmentSlotService;
    private final KafkaProducer kafkaProducer;

    public AppointmentServiceImpl(AppointmentRepo appointmentRepo,
                                  UserFeignClient userFeignClient,
                                  PaymentFeignClient paymentFeignClient,
                                  DoctorScheduleServiceImpl doctorScheduleService,
                                  AppointmentSlotServiceImpl appointmentSlotService,
                                  KafkaProducer kafkaProducer) {
        this.appointmentRepo = appointmentRepo;
        this.userFeignClient = userFeignClient;
        this.paymentFeignClient = paymentFeignClient;
        this.doctorScheduleService = doctorScheduleService;
        this.appointmentSlotService = appointmentSlotService;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public String createAppointment(AppointmentRequestDto appointmentRequestDto) {
        UserRequestDto userRequestDto = userFeignClient.getCurrentUserIdAndEmail();
        if(!appointmentSlotService.checkAvailability(appointmentRequestDto.getDoctorId(),
                appointmentRequestDto.getStartAppointment())){
            throw new AppointmentAlreadyBookedException("Appointment already booked");
        }


        Appointment appointment = Appointment.builder()
                .doctorId(appointmentRequestDto.getDoctorId())
                .startTime(appointmentRequestDto.getStartAppointment())
                .endTime(appointmentRequestDto.getStartAppointment().plusMinutes(30))
                .notes(appointmentRequestDto.getNotes())
                .patientId(userRequestDto.getUserId())
                .status(AppointmentStatus.CREATED)
                .build();
        appointment.setSlot(appointmentSlotService.getSlotByStartTimeAndDoctorId(appointmentRequestDto.getStartAppointment(), appointmentRequestDto.getDoctorId()));
        appointmentSlotService.reserveSlot(appointment.getSlot());
        appointmentRepo.save(appointment);
        return paymentFeignClient.createCheckoutSession(new PaymentRequestDto(
                appointmentRequestDto.getAmount(),
                "PLN",
                appointment.getId(),
                userRequestDto.getEmail()
        ));


    }

    @Override
    public void markPaymentAsFinalized(PaymentFinalizationEventDto paymentFinalizationEventDTO) {
        Appointment appointment = appointmentRepo.findById(paymentFinalizationEventDTO.getAppointmentId()).get();
        if (appointment==null) {
            throw new AppointmentNotFoundException("Appointment not found");
        }
        switch (paymentFinalizationEventDTO.getPaymentStatus()) {
            case SUCCEEDED -> appointment.setStatus(AppointmentStatus.CONFIRMED);
            case FAILED -> appointment.setStatus(AppointmentStatus.CANCELLED);
        }
        appointmentRepo.save(appointment);
        sendKafkaMessageAfterPaymentFinalization(appointment);

    }

    @Override
    public void sendKafkaMessageAfterPaymentFinalization(Appointment appointment) {
        kafkaProducer.sendKafkaAppointmentFinalizationEvent(
                new AppointmentFinalizationEventDto(
                        appointment.getId(),
                        appointment.getPatientId(),
                        appointment.getDoctorId()
        ));

    }

    @Override
    public List<AppointmentDto> getAppointments() {
        UserRequestDto userRequestDto = userFeignClient.getCurrentUserIdAndEmail();
        if (userRequestDto.getUserId()!=null) {
            List<Appointment> appointments = appointmentRepo.findAllByPatientId(userRequestDto.getUserId());
            return appointments.stream().map(AppointmentMapper::mapToAppointmentDto).toList();
        }else {
            throw new RuntimeException("User is not authorized to view appointments");
        }

    }
}
