package com.milicaradovanovic.daon.service;

import com.milicaradovanovic.daon.entity.AirportUserEntity;
import com.milicaradovanovic.daon.repository.AirportUserRepository;
import com.milicaradovanovic.daon.security.JwtProvider;
import com.milicaradovanovic.daon.security.UserPrincipalImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AirportUserService {
    private AirportUserRepository airportUserRepository;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;

    @Autowired
    public AirportUserService(AuthenticationManager authenticationManager, JwtProvider jwtProvider,
                              AirportUserRepository airportUserRepository) {
        this.airportUserRepository = airportUserRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public AirportUserEntity getCurrentUser(Authentication authentication) {
        Optional<AirportUserEntity> airportUser = this.airportUserRepository
                .findById((int) ((UserPrincipalImpl) authentication.getPrincipal()).getUserId());

        if (airportUser.isPresent()) {
            return airportUser.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Optional<HashMap<String, Object>> login(String email, String password) {
        HashMap<String, Object> tokens = new HashMap<>();

        try {
            UserPrincipalImpl principal =
                    (UserPrincipalImpl) this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,
                            password)).getPrincipal();
            this.setUpTokens(tokens, principal);

        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new InsufficientAuthenticationException("Bad Credentials");
        }
        return Optional.of(tokens);
    }

    private void setUpTokens(HashMap<String, Object> tokens, UserPrincipalImpl principal) {
        tokens.put("access", this.jwtProvider.createAccessToken(principal.getAirportUser()));
    }
}
