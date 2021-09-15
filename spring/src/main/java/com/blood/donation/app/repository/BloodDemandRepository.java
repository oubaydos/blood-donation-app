package com.blood.donation.app.repository;

import com.blood.donation.app.model.BloodDemand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodDemandRepository extends JpaRepository<BloodDemand, Long> {
}