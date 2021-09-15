package com.blood.donation.app.model;

import com.blood.donation.app.enums.BloodType;
import com.blood.donation.app.enums.City;
import com.blood.donation.app.enums.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Objects;


/**
 * Could be either a
 *  DONOR
 *  BLOOD DEMANDER
 */
@Entity
public class Donor {
    @Id
    @NotNull(message = "Email must not be null")
    @javax.validation.constraints.Email
    private String email;

    @NotNull(message = "password must not be null")
    @NotEmpty(message = "password must not be an empty string")
    private String password;

    @NotNull(message = "firstName must not be null")
    @NotEmpty(message = "firstName must not be an empty string")
    private String firstName;

    @NotNull(message = "lastName must not be null")
    @NotEmpty(message = "lastName must not be an empty string")
    private String lastName;

    @NotNull(message = "Age must not be null")
    @Min(value = 18, message = "Age must be greater than 18")
    private int age;

    @NotNull(message = "city must not be null")
    private City city;

    // could change if i decided to handle admin or other roles
    private Role role = Role.USER;

    private BloodType bloodType = BloodType.UNKNOWN;

    @PositiveOrZero
    private int numberOfTimesDonated = 0;

    @PositiveOrZero
    private int numberOfTimesAsked = 0;

    private Instant lastTimeDonated;

    @Column(nullable=false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean canDonate = false;

    public Donor() {
    }

    public Donor(String email, String password, String firstName, String lastName, int age, City city, BloodType bloodType) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.city = city;
        this.bloodType = bloodType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
    public void setCity(String city) {
        this.city = City.fromValue(city);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }
    public void setBloodType(String bloodType) {
        this.bloodType = BloodType.fromValue(bloodType);
    }

    public int getNumberOfTimesDonated() {
        return numberOfTimesDonated;
    }

    public void setNumberOfTimesDonated(int numberOfTimesDonated) {
        this.numberOfTimesDonated = numberOfTimesDonated;
    }

    public int getNumberOfTimesAsked() {
        return numberOfTimesAsked;
    }

    public void setNumberOfTimesAsked(int numberOfTimesAsked) {
        this.numberOfTimesAsked = numberOfTimesAsked;
    }

    public Instant getLastTimeDonated() {
        return lastTimeDonated;
    }

    public void setLastTimeDonated(Instant lastTimeDonated) {
        this.lastTimeDonated = lastTimeDonated;
    }

    public boolean isCanDonate() {
        return canDonate;
    }

    public void setCanDonate(boolean canDonate) {
        this.canDonate = canDonate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donor donor = (Donor) o;
        return email.equals(donor.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "Donor{" +
                "Email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", city=" + city +
                ", bloodType=" + bloodType +
                ", numberOfTimesDonated=" + numberOfTimesDonated +
                ", numberOfTimesAsked=" + numberOfTimesAsked +
                ", lastTimeDonated=" + lastTimeDonated +
                '}';
    }
}
