package com.blood.donation.app.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.blood.donation.app.exception.MissingAuthorizationHeaderException;
import com.blood.donation.app.exception.TokenException;
import com.blood.donation.app.model.Donor;
import com.blood.donation.app.service.DonorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.blood.donation.app.filter.Utils.generateAccessToken;
import static com.blood.donation.app.filter.Utils.tokenHashingAlgorithm;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class RefreshToken {
    private static final Logger LOG = LoggerFactory.getLogger(RefreshToken.class);
    private final DonorService donorService;

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String refreshToken = authorizationHeader.substring(7);
            LOG.debug("received token: {}", refreshToken);
            JWTVerifier verifier = JWT.require(tokenHashingAlgorithm).build();
            DecodedJWT decodedJWT;
            try {
                decodedJWT = verifier.verify(refreshToken);
            } catch (JWTVerificationException e) {
                throw new TokenException(e);
            }
            String email = decodedJWT.getSubject();
            Donor user = donorService.getDonor(email);
            String accessToken = generateAccessToken(request, user);
            response.setHeader("access_token", accessToken);
            response.setHeader("refresh_token", refreshToken);
            // add to body
            Map<String, String> tokens = Map.of(
                    "access_token", accessToken,
                    "refresh_token", refreshToken
            );
            response.setContentType(APPLICATION_JSON_VALUE);
            try {
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        } else {
            throw new MissingAuthorizationHeaderException();
        }
    }
}
