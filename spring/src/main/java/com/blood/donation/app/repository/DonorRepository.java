package com.blood.donation.app.repository;

import com.blood.donation.app.enums.BloodType;
import com.blood.donation.app.enums.City;
import com.blood.donation.app.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DonorRepository extends JpaRepository<Donor, String> {
    Set<Donor> findAllByCityAndBloodTypeInAndCanDonateIsTrue(City city,Set<BloodType> bloodType);
}
