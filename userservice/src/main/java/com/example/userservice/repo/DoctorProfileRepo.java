package com.example.userservice.repo;

import com.example.userservice.model.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorProfileRepo extends JpaRepository<DoctorProfile,Long> {
}
