package com.khanghn.careerspark_api.configuration;

import com.khanghn.careerspark_api.exception.handler.CustomAccessDeniedHandler;
import com.khanghn.careerspark_api.exception.handler.CustomAuthenticationEntryPoint;
import com.khanghn.careerspark_api.filter.JwtAuthenticationFilter;
import com.khanghn.careerspark_api.security.CustomOauth2UserService;
import com.khanghn.careerspark_api.security.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import static com.khanghn.careerspark_api.security.PublicEndpoints.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomOauth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] SWAGGER_UI_ENDPOINTS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    private static final PathPatternRequestMatcher[] GENERAL_ENDPOINTS = {
            PathPatternRequestMatcher.pathPattern(HttpMethod.GET, "/nothing/**"),
    };

    private static final PathPatternRequestMatcher[] ADMIN_ENDPOINTS = {
            //Package API
            PathPatternRequestMatcher.pathPattern(HttpMethod.POST, "/api/packages"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.PUT, "/api/packages**"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.DELETE, "/api/packages**"),

            //Sector API
            PathPatternRequestMatcher.pathPattern(HttpMethod.GET, "/api/sectors/all"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.POST, "/api/sectors"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.PUT, "/api/sectors**"),

            //Position API
            PathPatternRequestMatcher.pathPattern(HttpMethod.GET, "/api/positions"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.POST, "/api/positions"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.PUT, "/api/positions**"),

            //User API
            PathPatternRequestMatcher.pathPattern(HttpMethod.DELETE, "/api/users"),
    };

    private static final PathPatternRequestMatcher[] RECRUITER_ENDPOINTS = {
            PathPatternRequestMatcher.pathPattern(HttpMethod.POST, "/api/companies"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.PUT, "/api/companies**"),
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(SWAGGER_UI_ENDPOINTS).permitAll()
                        .requestMatchers(GENERAL_ENDPOINTS).hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(RECRUITER_ENDPOINTS).hasRole("RECRUITER")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler((request, response, exception) -> response.sendError(401, "OAuth2 login failed")))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
