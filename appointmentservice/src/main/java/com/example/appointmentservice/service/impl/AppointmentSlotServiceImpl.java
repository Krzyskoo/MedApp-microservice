package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.constants.AppointmentSlotStatus;
import com.example.appointmentservice.dto.AppointmentSlotDto;
import com.example.appointmentservice.exception.AppointmentSlotNotFoundException;
import com.example.appointmentservice.mapper.AppointmentSlotMapper;
import com.example.appointmentservice.model.AppointmentSlot;
import com.example.appointmentservice.model.DoctorSchedule;
import com.example.appointmentservice.repo.AppointmentSlotRepo;
import com.example.appointmentservice.service.IAppointmentSlotService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentSlotServiceImpl implements IAppointmentSlotService {
    private final DoctorScheduleServiceImpl doctorScheduleService;
    private final AppointmentSlotRepo appointmentSlotRepo;

    public AppointmentSlotServiceImpl(DoctorScheduleServiceImpl doctorScheduleService,
                                      AppointmentSlotRepo appointmentSlotRepo) {
        this.doctorScheduleService = doctorScheduleService;
        this.appointmentSlotRepo = appointmentSlotRepo;
    }

    @Override
    public List<AppointmentSlot> getAvailablesSlots(Long doctorId, LocalDate date) {
        return null;
    }

    @Override
    public void createSlotsWeakly() {
        List<DoctorSchedule> schedules = doctorScheduleService.getWeeklyScheduleForAllDoctors();
        List<AppointmentSlot> slots = new ArrayList<>();
        schedules.forEach(schedule ->{
            while (schedule.getWorkStart().isBefore(schedule.getWorkEnd())){
                AppointmentSlot slot = new AppointmentSlot();
                slot.setDoctorId(schedule.getDoctorId());
                slot.setStartTime(LocalDateTime.of(schedule.getWorkDate(), schedule.getWorkStart()));
                slot.setEndTime(LocalDateTime.of(schedule.getWorkDate(), schedule.getWorkStart().plusMinutes(30)));
                slot.setStatus(AppointmentSlotStatus.AVAILABLE);
                slots.add(slot);
                schedule.setWorkStart(schedule.getWorkStart().plusMinutes(30));
            }
        });
        appointmentSlotRepo.saveAll(slots);
    }

    @Override
    public boolean checkAvailability(Long doctorId, LocalDateTime startTime) {
        AppointmentSlot slot = appointmentSlotRepo.findByStartTimeAndDoctorId(startTime, doctorId).orElseThrow(
                () -> new AppointmentSlotNotFoundException("Slot not found")
        );
        if (slot.getStatus().equals(AppointmentSlotStatus.BOOKED)){
            return false;
        }
        return true;

    }

    @Override
    public void reserveSlot(AppointmentSlot slot) {
        slot.setStatus(AppointmentSlotStatus.BOOKED);
        appointmentSlotRepo.save(slot);
    }

    @Override
    public AppointmentSlot getSlotByStartTimeAndDoctorId(LocalDateTime startTime, Long doctorId) {
        return appointmentSlotRepo.findByStartTimeAndDoctorId(startTime, doctorId).orElseThrow(
                () -> new AppointmentSlotNotFoundException("Slot not found")
        ) ;
    }

    @Override
    public List<AppointmentSlotDto> getSlotsByDoctorAndDate(Long doctorId) {
        List<AppointmentSlot> slots = appointmentSlotRepo.findAllByDoctorIdAndAndStartTimeIsAfter(doctorId, LocalDateTime.now());
        return slots
                .stream()
                .map(AppointmentSlotMapper::mapToAppointmentSlotDto)
                .toList();
    }
}
