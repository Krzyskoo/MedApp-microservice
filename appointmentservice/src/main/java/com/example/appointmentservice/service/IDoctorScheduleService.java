package com.example.appointmentservice.service;

import com.example.appointmentservice.model.DoctorSchedule;

import java.util.List;

public interface IDoctorScheduleService {

    void createMonthlySchedule();
    void createDailySchedule(Long doctorId);
    void modifySchedule(Long doctorId);
    void runMonthlyGenerator();
    List<DoctorSchedule> getWeeklyScheduleForAllDoctors();
}
