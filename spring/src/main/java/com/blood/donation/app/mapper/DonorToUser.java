package com.blood.donation.app.mapper;

import com.blood.donation.app.model.Donor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class DonorToUser extends User {
    public DonorToUser(Donor donor) {
        super(
                donor.getEmail(),
                donor.getPassword(),
                List.of(new SimpleGrantedAuthority(donor.getRole().value()))
        );
    }
}
