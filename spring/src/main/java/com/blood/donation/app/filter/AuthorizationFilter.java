package com.blood.donation.app.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.blood.donation.app.enums.Role;
import com.blood.donation.app.exception.MissingAuthorizationHeaderException;
import com.blood.donation.app.exception.TokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.blood.donation.app.filter.Utils.authenticationPaths;
import static com.blood.donation.app.filter.Utils.verifier;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthorizationFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (authenticationPaths.contains(request.getServletPath())) {
            LOG.debug("Authentication filter path: " + request.getServletPath());
            // if wanted to be authenticated and not authorized, pass to the next filter (authentication)
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            LOG.debug("received token: {}", token);
            DecodedJWT decodedJWT;
            try {
                decodedJWT = verifier.verify(token);
            } catch (JWTVerificationException e) {
                throw new TokenException(e);
            }
            String email = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim(Role.class.getSimpleName()).asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (roles == null) {
                LOG.error("roles array is empty");
                return;
            }
            Arrays.stream(roles).forEach(
                    authority -> authorities.add(new SimpleGrantedAuthority(authority))
            );
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            throw new MissingAuthorizationHeaderException();
        }
        filterChain.doFilter(request, response);
    }
}
