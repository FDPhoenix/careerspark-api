package com.khanghn.careerspark_api.configuration;

import com.khanghn.careerspark_api.exception.handler.CustomAccessDeniedHandler;
import com.khanghn.careerspark_api.exception.handler.CustomAuthenticationEntryPoint;
import com.khanghn.careerspark_api.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import static com.khanghn.careerspark_api.security.PublicEndpoints.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] SWAGGER_UI_ENDPOINTS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    private static final PathPatternRequestMatcher[] GENERAL_ENDPOINTS = {
            PathPatternRequestMatcher.pathPattern(HttpMethod.GET, "/nothing/**"),
    };

    private static final PathPatternRequestMatcher[] ADMIN_ENDPOINTS = {
            PathPatternRequestMatcher.pathPattern(HttpMethod.POST, "/packages/"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.PUT, "/packages/**"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.DELETE, "/packages/**"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.DELETE, "/users/"),
    };

    private static final PathPatternRequestMatcher[] RECRUITER_ENDPOINTS = {
            PathPatternRequestMatcher.pathPattern(HttpMethod.GET, "/nothing/also"),
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter, CustomAuthenticationEntryPoint authenticationEntryPoint, CustomAccessDeniedHandler accessDeniedHandler) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(SWAGGER_UI_ENDPOINTS).permitAll()
                        .requestMatchers(GENERAL_ENDPOINTS).hasAnyRole("ADMIN", "RECRUITER")
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
