package com.blood.donation.app.model;

import com.blood.donation.app.enums.BloodType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

/*
REMOVE BLOOD_TYPE CUZ ITS ALREADY IN DONOR
 */
@Entity
public class BloodDemand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private Instant instant;

    @NotNull
    private BloodType bloodType;

    @PositiveOrZero
    private float amountOfBloodNeeded = 0;


    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_email")
    private Donor donor;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "blood_demand_id")
    private Set<Donor> possibleDonors;

    public BloodDemand( Instant instant, BloodType bloodType, float amountOfBloodNeeded, Donor donor, Set<Donor> possibleDonors) {
        this.instant = instant;
        this.bloodType = bloodType;
        this.amountOfBloodNeeded = amountOfBloodNeeded;
        this.donor = donor;
        this.possibleDonors = possibleDonors;
    }

    public BloodDemand( Instant instant, BloodType bloodType, float amountOfBloodNeeded, Donor donor) {
        this.instant = instant;
        this.bloodType = bloodType;
        this.amountOfBloodNeeded = amountOfBloodNeeded;
        this.donor = donor;
    }

    public BloodDemand() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public float getAmountOfBloodNeeded() {
        return amountOfBloodNeeded;
    }

    public void setAmountOfBloodNeeded(float amountOfBloodNeeded) {
        this.amountOfBloodNeeded = amountOfBloodNeeded;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public Set<Donor> getPossibleDonors() {
        return possibleDonors;
    }

    public void setPossibleDonors(Set<Donor> possibleDonors) {
        this.possibleDonors = possibleDonors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloodDemand that = (BloodDemand) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BloodDemand{" +
                "id=" + id +
                ", instant=" + instant +
                ", bloodType=" + bloodType +
                ", amountOfBloodNeeded=" + amountOfBloodNeeded +
                ", donor=" + donor +
                ", possibleDonors=" + possibleDonors +
                '}';
    }

}
