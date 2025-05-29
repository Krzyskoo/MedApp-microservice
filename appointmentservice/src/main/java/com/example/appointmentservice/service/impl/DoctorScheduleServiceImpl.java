package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.dto.UserDoctorDto;
import com.example.appointmentservice.model.DoctorSchedule;
import com.example.appointmentservice.repo.DoctorScheduleRepo;
import com.example.appointmentservice.service.IDoctorScheduleService;
import com.example.appointmentservice.service.client.UserFeignClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DoctorScheduleServiceImpl implements IDoctorScheduleService {

    private final DoctorScheduleRepo doctorScheduleRepo;
    private final UserFeignClient userFeignClient;

    public DoctorScheduleServiceImpl(DoctorScheduleRepo doctorScheduleRepo, UserFeignClient userFeignClient) {
        this.doctorScheduleRepo = doctorScheduleRepo;
        this.userFeignClient = userFeignClient;
    }

    @Override
    public void runMonthlyGenerator() {

    }

    @Override
    public void createMonthlySchedule() {

        List<UserDoctorDto> doctors = userFeignClient.getAllDoctors();
        YearMonth currentMonth = YearMonth.now();
        List<LocalDate> allDates =IntStream.rangeClosed(1,currentMonth.lengthOfMonth())
                .mapToObj(currentMonth::atDay)
                .collect(Collectors.toList());
        List<DoctorSchedule> schedules = new ArrayList<>();

        doctors.forEach(doctor -> {
            allDates.forEach(date -> {
                DoctorSchedule schedule = new DoctorSchedule();
                schedule.setDoctorId(doctor.getDoctorId());
                schedule.setWorkStart(LocalTime.of(8, 0));
                schedule.setWorkEnd(LocalTime.of(16, 0));
                schedule.setWorkDate(date);
                schedule.setWeekday(date.getDayOfWeek());
                schedules.add(schedule);
            });
        });
        doctorScheduleRepo.saveAll(schedules);
    }

    @Override
    public void createDailySchedule(Long doctorId) {

    }

    @Override
    public void modifySchedule(Long doctorId) {

    }

    @Override
    public List<DoctorSchedule> getWeeklyScheduleForAllDoctors() {
        return doctorScheduleRepo.findByWorkDateBetween(LocalDate.now(), LocalDate.now().plusDays(7));
    }
}
