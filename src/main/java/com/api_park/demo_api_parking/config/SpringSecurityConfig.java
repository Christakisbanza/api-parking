package com.api_park.demo_api_parking.config;


import com.api_park.demo_api_parking.jwt.JwtAuthenticationEntryPoint;
import com.api_park.demo_api_parking.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableMethodSecurity
@EnableWebMvc
@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST,"api/v1/users").permitAll()
                    .requestMatchers(HttpMethod.POST,"api/v1/auth").permitAll()
                    .requestMatchers(
                        antMatcher(HttpMethod.POST, "/api/v1/users"),
                        antMatcher(HttpMethod.POST, "/api/v1/auth"),
                        antMatcher("/docs-park.html"),
                        antMatcher("/docs-park/**"),
                        antMatcher("/swagger-ui.html"),
                        antMatcher("/swagger-ui/**"),
                        antMatcher("/webjars/**")
                    ).permitAll()
                    .anyRequest().authenticated()
                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(ex ->
                    ex.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                )
                .build();

    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
