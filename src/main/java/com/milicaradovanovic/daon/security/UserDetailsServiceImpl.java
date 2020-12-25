package com.milicaradovanovic.daon.security;

import com.milicaradovanovic.daon.entity.AirportUserEntity;
import com.milicaradovanovic.daon.repository.AirportUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private AirportUserRepository userLoginRepository;
    private JwtProvider jwtProvider;


    @Autowired
    public UserDetailsServiceImpl(AirportUserRepository userLoginRepository,
                                  JwtProvider jwtProvider) {
        this.userLoginRepository = userLoginRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AirportUserEntity user = this.userLoginRepository.findByEmail(s).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with email %s does not exist", s)));

        return new UserPrincipalImpl(user);
    }

    public Optional<UserPrincipalImpl> loadUserByJwtToken(String jwtToken) {
        if (this.jwtProvider.isValidToken(jwtToken)) {
            AirportUserEntity airportUser = new AirportUserEntity();
            airportUser.setEmail(this.jwtProvider.getEmail(jwtToken));
            airportUser.setId((int) this.jwtProvider.getUserId(jwtToken));
            return Optional.of(new UserPrincipalImpl(airportUser));
        }
        return Optional.empty();
    }
}
