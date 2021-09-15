package com.blood.donation.app.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.blood.donation.app.exception.TokenIOException;
import com.blood.donation.app.exception.UserAuthenticationException;
import com.blood.donation.app.exception.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static com.blood.donation.app.filter.Utils.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//        x-www-form-urlencoded
        Authentication rst;
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            LOG.debug("Attempting to authenticate with email: {} and password: {}", email, password);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
            rst = authenticationManager.authenticate(token);
        } catch (Exception e) {
            throw new UserAuthenticationException(e);
        }
        return rst;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        Map<String, String> tokens = null;
        try {
            User user = (User) authResult.getPrincipal();
            String accessToken = generateAccessToken(request, user);
            String refreshToken = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenLifeTime))
                    .withIssuer(request.getRequestURL().toString())
                    .sign(tokenHashingAlgorithm);
            // add to header
            response.setHeader("access_token", accessToken);
            response.setHeader("refresh_token", refreshToken);
            // add to body
            tokens = Map.of(
                    "access_token", accessToken,
                    "refresh_token", refreshToken
            );
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } catch (IOException e) {
            throw new TokenIOException(e);
        } catch (Exception e) {
            throw new UserAuthenticationException(e);
        }
    }

}
