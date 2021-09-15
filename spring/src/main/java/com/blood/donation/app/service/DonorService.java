package com.blood.donation.app.service;

import com.blood.donation.app.enums.BloodType;
import com.blood.donation.app.enums.City;
import com.blood.donation.app.exception.UserEmailDuplicatedException;
import com.blood.donation.app.mapper.DonorToUser;
import com.blood.donation.app.model.BloodDemand;
import com.blood.donation.app.model.Donor;
import com.blood.donation.app.repository.BloodDemandRepository;
import com.blood.donation.app.repository.DonorRepository;
import com.blood.donation.app.utils.TESTING;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.blood.donation.app.utils.Utils.compatibilityMap;
import static com.blood.donation.app.utils.Utils.minimumTimeBetweenDonations;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
/*
CHANGE THE CAN_DONATE FIELD EVERY TIME A DONOR DONATE OR SIGN UP
 */
public class DonorService implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(DonorService.class);
    private final DonorRepository donorRepository;
    private final BloodDemandService bloodDemandService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Donor> donor = donorRepository.findById(s);
        if (donor.isEmpty()){
            throw new UsernameNotFoundException("username not found : "+s);
        }
        LOG.debug("user found  : "+s);
        return new DonorToUser(donor.get());
    }
    public void signUpDonor(@NotNull Donor donor){
        if (donorRepository.existsById(donor.getEmail())){
            throw new UserEmailDuplicatedException(donor.getEmail());
        }
        donor.setPassword(bCryptPasswordEncoder.encode(donor.getPassword()));
        donor.setCanDonate(canDonate(donor));
        donorRepository.save(donor);
    }


    @TESTING
    public String checkIfUserExists(@NotNull String email,@NotNull String password) {
        Optional<Donor> donor =  donorRepository.findById(email);
        if (donor.isEmpty())
            return "user not found";
        if (bCryptPasswordEncoder.matches(password,donor.get().getPassword()))
            return "found and authenticated";
        LOG.debug("old password : {}",donor.get().getPassword());
        LOG.debug("new password : {}",bCryptPasswordEncoder.encode(password));

        return "bad credentials";
    }

    public Donor getDonor(@NotNull String email) {
        Optional<Donor> donor =  donorRepository.findById(email);
        if (donor.isEmpty()) return null;
        return donor.get();
    }
    public boolean canDonate(@NotNull Donor donor){
        if (donor.getLastTimeDonated()==null)
            return true;
        return donor.getLastTimeDonated().plus(minimumTimeBetweenDonations, ChronoUnit.DAYS).isBefore(Instant.now());
    }

    public Set<Donor> getCompatibleDonors(City city, BloodType bloodType) {
        return donorRepository.findAllByCityAndBloodTypeInAndCanDonateIsTrue(city,compatibilityMap.get(bloodType));
    }
}
