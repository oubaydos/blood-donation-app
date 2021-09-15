package com.blood.donation.app.utils;

import com.blood.donation.app.enums.BloodType;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static final String loginPath = "/login";
    public static final String refreshTokenPath = "/refreshToken";
    public static final Map<BloodType, Set<BloodType>>
            compatibilityMap = Map.of(
            BloodType.O_NEG, Set.of(BloodType.O_NEG),
            BloodType.O_POS, Set.of(BloodType.O_POS, BloodType.O_NEG),
            BloodType.A_NEG, Set.of(BloodType.O_NEG, BloodType.A_NEG),
            BloodType.A_POS, Set.of(BloodType.O_NEG, BloodType.O_POS, BloodType.A_NEG, BloodType.A_POS),
            BloodType.B_NEG, Set.of(BloodType.O_NEG, BloodType.B_NEG),
            BloodType.B_POS, Set.of(BloodType.O_NEG, BloodType.O_POS, BloodType.B_NEG, BloodType.B_POS),
            BloodType.AB_NEG, Set.of(BloodType.O_NEG, BloodType.A_NEG, BloodType.B_NEG, BloodType.AB_NEG),
            BloodType.AB_POS, Set.of(BloodType.O_NEG, BloodType.O_POS, BloodType.A_NEG, BloodType.A_POS, BloodType.B_NEG, BloodType.B_POS, BloodType.AB_NEG, BloodType.AB_POS, BloodType.UNKNOWN),
            BloodType.UNKNOWN, Set.of(BloodType.O_NEG)
    );
    public static long minimumTimeBetweenDonations= TimeUnit.DAYS.toDays(56);//56 days


    public static void main(String[] args) {
        System.err.println(minimumTimeBetweenDonations);
    }
}
