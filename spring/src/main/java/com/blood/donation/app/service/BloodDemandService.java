package com.blood.donation.app.service;

import com.blood.donation.app.model.BloodDemand;
import com.blood.donation.app.model.Donor;
import com.blood.donation.app.repository.BloodDemandRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
//@RequiredArgsConstructor
public class BloodDemandService {
    private static final Logger LOG = LoggerFactory.getLogger(BloodDemandService.class);
    private final BloodDemandRepository bloodDemandRepository;
    private final DonorService donorService;

    public BloodDemandService(BloodDemandRepository bloodDemandRepository, @Lazy DonorService donorService) {
        this.bloodDemandRepository = bloodDemandRepository;
        this.donorService = donorService;
    }

    public void save(BloodDemand bloodDemand) {
        bloodDemandRepository.save(bloodDemand);
    }

    public void addBloodRequest(BloodDemand demand) {
        demand.setPossibleDonors(generatePossibleDonors(demand));
        bloodDemandRepository.save(demand);
    }

    private Set<Donor> generatePossibleDonors(BloodDemand demand) {
        return donorService.getCompatibleDonors(demand.getDonor().getCity(),demand.getDonor().getBloodType());
    }
}
