package com.milicaradovanovic.daon.security;

import com.milicaradovanovic.daon.entity.AirportUserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipalImpl implements UserDetails {


    private AirportUserEntity airportUser;

    public UserPrincipalImpl(AirportUserEntity airportUser) {
        super();
        this.airportUser = airportUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override
    public String getPassword() {
        return this.airportUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.airportUser.getEmail();
    }

    public long getUserId() {
        return this.airportUser.getId();
    }

    public AirportUserEntity getAirportUser() {
        return this.airportUser;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
