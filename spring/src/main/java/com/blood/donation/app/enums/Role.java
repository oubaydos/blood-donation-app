package com.blood.donation.app.enums;

public enum Role {
    USER("user");
    private final String value;

    Role(String value) {
        this.value = value;
    }
    public final String value(){
        return value;
    }
    public static Role fromValue (String value) {
        for(Role role : Role.values())
            if(role.value.equals(value)) return role;
        return null;
    }
}
