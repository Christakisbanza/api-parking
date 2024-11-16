package com.api_park.demo_api_parking.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class JwtUserDetails extends User {

    private com.api_park.demo_api_parking.entity.User user;

    public JwtUserDetails(com.api_park.demo_api_parking.entity.User user) {
        super(user.getUserName(), user.getPassWord(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public Long getId(){
        return user.getId();
    }

    public String getRole(){
        return user.getRole().name();
    }
}
