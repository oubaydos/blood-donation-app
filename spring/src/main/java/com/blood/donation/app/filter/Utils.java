package com.blood.donation.app.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.blood.donation.app.enums.Role;
import com.blood.donation.app.model.Donor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.blood.donation.app.utils.Utils.loginPath;
import static com.blood.donation.app.utils.Utils.refreshTokenPath;


public class Utils {
    // expiration date == 15 min
    public static final long accessTokenLifeTime = TimeUnit.MINUTES.toMillis(15);
    // 6 months
    public static final long refreshTokenLifeTime = TimeUnit.DAYS.toMillis(6 * 30);
    public static List<String> authenticationPaths = List.of(loginPath,refreshTokenPath);
    public static final Algorithm tokenHashingAlgorithm = Algorithm.HMAC256(System.getenv("HASH_KEY").getBytes());
    public static final JWTVerifier verifier = JWT.require(tokenHashingAlgorithm).build();

    public static String generateAccessToken(HttpServletRequest request, User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenLifeTime))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(
                        Role.class.getSimpleName(),
                        user
                                .getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .sign(tokenHashingAlgorithm);
    }
    public static String generateAccessToken(HttpServletRequest request, Donor user){
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenLifeTime))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(
                        Role.class.getSimpleName(),
                        List.of(user.getRole().value())
                )
                .sign(tokenHashingAlgorithm);
    }

}
