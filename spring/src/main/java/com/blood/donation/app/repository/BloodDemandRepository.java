package com.blood.donation.app.repository;

import com.blood.donation.app.model.BloodDemand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodDemandRepository extends JpaRepository<BloodDemand, long> {
}