package com.example.appointmentservice.repo;

import com.example.appointmentservice.model.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorScheduleRepo extends JpaRepository<DoctorSchedule, Long> {
    Optional<DoctorSchedule> findByDoctorIdAndWorkDate(Long doctorId, LocalDate workDate);
    Optional<DoctorSchedule> findByDoctorIdAndWeekday(Long doctorId, String weekday);
    List<DoctorSchedule> findByWorkDateBetween(LocalDate startDate, LocalDate endDate);
}
