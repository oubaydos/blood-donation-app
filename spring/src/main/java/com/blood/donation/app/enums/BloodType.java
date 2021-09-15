package com.blood.donation.app.enums;

public enum BloodType {
    O_POS("O+"),
    O_NEG("O-"),
    A_POS("A+"),
    A_NEG("A-"),
    B_POS("B+"),
    B_NEG("B-"),
    AB_POS("AB+"),
    AB_NEG("AB-"),
    UNKNOWN("unknown");

    private final String value;

    BloodType(String value) {
        this.value = value;
    }

    public final String value() { return this.value; }

    public static BloodType fromValue (String value) {
        for(BloodType requestName : BloodType.values())
            if(requestName.value.equals(value)) return requestName;
        return UNKNOWN;
    }
}
