package com.example.paymentservice.repo;

import com.example.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {
    Payment findByIntentId(String intentId);
    Payment findByAppointmentId(Long appointmentId);
}
