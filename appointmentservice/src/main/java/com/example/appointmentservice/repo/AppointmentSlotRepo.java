package com.example.appointmentservice.repo;

import com.example.appointmentservice.model.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentSlotRepo extends JpaRepository<AppointmentSlot, Long> {
    Optional<AppointmentSlot> findByStartTimeAndDoctorId(LocalDateTime startTime, Long doctorId);
    List<AppointmentSlot> findAllByDoctorIdAndAndStartTimeIsAfter(Long doctorId, LocalDateTime startTime);
}
