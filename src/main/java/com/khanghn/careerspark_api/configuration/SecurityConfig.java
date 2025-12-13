package com.khanghn.careerspark_api.configuration;

import com.khanghn.careerspark_api.exception.handler.CustomAccessDeniedHandler;
import com.khanghn.careerspark_api.exception.handler.CustomAuthenticationEntryPoint;
import com.khanghn.careerspark_api.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.khanghn.careerspark_api.security.PublicEndpoints.PERMIT_ENDPOINTS;
import static com.khanghn.careerspark_api.security.PublicEndpoints.SWAGGER_UI_ENDPOINTS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final String[] GENERAL_ENDPOINTS = {
            "/nothing"
    };

    private final String[] ADMIN_ENDPOINTS = {
            "/user/"
    };

    private final String[] RECRUITER_ENDPOINTS = {
            "/nothing/also"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter, CustomAuthenticationEntryPoint authenticationEntryPoint, CustomAccessDeniedHandler accessDeniedHandler) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PERMIT_ENDPOINTS).permitAll()
                        .requestMatchers(SWAGGER_UI_ENDPOINTS).permitAll()
                        .requestMatchers(GENERAL_ENDPOINTS).hasAnyRole("RECRUITER", "ADMIN")
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(RECRUITER_ENDPOINTS).hasRole("RECRUITER")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
