package com.api_park.demo_api_parking.jwt;

import com.api_park.demo_api_parking.entity.User;
import com.api_park.demo_api_parking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsServices implements UserDetailsService {

    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String name){
        User.Role role = userService.findRoleByUserName(name);
        return JwtUtils.createToken(name, role.name().substring("ROLE_".length()));
    }
}
