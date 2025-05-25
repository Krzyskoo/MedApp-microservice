package com.example.appointmentservice.service;

public interface IDoctorScheduleService {

    void createMonthlySchedule(Long doctorId);
    void createDailySchedule(Long doctorId);
    void modifySchedule(Long doctorId);
    void runMonthlyGenerator();
}
