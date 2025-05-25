package com.example.appointmentservice.repo;

import com.example.appointmentservice.model.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorScheduleRepo extends JpaRepository<DoctorSchedule, Long> {
}
