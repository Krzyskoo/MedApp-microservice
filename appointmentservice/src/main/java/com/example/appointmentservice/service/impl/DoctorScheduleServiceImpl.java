package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.model.DoctorSchedule;
import com.example.appointmentservice.repo.DoctorRepo;
import com.example.appointmentservice.repo.DoctorScheduleRepo;
import com.example.appointmentservice.service.IDoctorScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DoctorScheduleServiceImpl implements IDoctorScheduleService {

    private final DoctorRepo doctorRepo;
    private final DoctorScheduleRepo doctorScheduleRepo;

    public DoctorScheduleServiceImpl(DoctorRepo doctorRepo, DoctorScheduleRepo doctorScheduleRepo) {
        this.doctorRepo = doctorRepo;
        this.doctorScheduleRepo = doctorScheduleRepo;
    }

    @Override
    public void runMonthlyGenerator() {

    }

    @Override
    public void createMonthlySchedule(Long doctorId) {
        YearMonth currentMonth = YearMonth.now();
        List<LocalDate> allDates =IntStream.rangeClosed(1,currentMonth.lengthOfMonth())
                .mapToObj(currentMonth::atDay)
                .collect(Collectors.toList());
        List<DoctorSchedule> schedules = new ArrayList<>();
        ZoneId zone = ZoneId.systemDefault();
        Random random = new Random();
        for (LocalDate date : allDates) {
            LocalTime start = random.nextBoolean() ? LocalTime.of(8, 0) : LocalTime.of(16, 0);
            LocalTime end = start.plusHours(8);

            DoctorSchedule doctorSchedule = new DoctorSchedule();
            doctorSchedule.setDoctor(doctorRepo.findById(doctorId).get());
            doctorSchedule.setWeekday(date.getDayOfWeek().name());
            doctorSchedule.setWorkDate(Date.from(date.atStartOfDay(zone).toInstant()));
            doctorSchedule.setWorkStart(start);
            doctorSchedule.setWorkEnd(end);
            schedules.add(doctorSchedule);
        }
        doctorScheduleRepo.saveAll(schedules);

    }

    @Override
    public void createDailySchedule(Long doctorId) {

    }

    @Override
    public void modifySchedule(Long doctorId) {

    }


}
