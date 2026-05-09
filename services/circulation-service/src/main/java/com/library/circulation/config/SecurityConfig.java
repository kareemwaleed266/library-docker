package com.library.circulation.config;

import com.library.circulation.security.JwtAuthenticationFilter;
import com.library.common.security.RoleConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/actuator/health",
                                "/actuator/info"
                        ).permitAll()

                        .requestMatchers("/internal/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/borrows").hasRole(RoleConstants.USER)
                        .requestMatchers(HttpMethod.GET, "/api/borrows/my").hasRole(RoleConstants.USER)

                        .requestMatchers(HttpMethod.POST, "/api/reservations").hasRole(RoleConstants.USER)
                        .requestMatchers(HttpMethod.GET, "/api/reservations/my").hasRole(RoleConstants.USER)
                        .requestMatchers(HttpMethod.PATCH, "/api/reservations/*/cancel")
                        .hasAnyRole(RoleConstants.USER, RoleConstants.ADMIN, RoleConstants.LIBRARIAN)

                        .requestMatchers("/api/borrows/**")
                        .hasAnyRole(RoleConstants.ADMIN, RoleConstants.LIBRARIAN)

                        .requestMatchers("/api/reservations/**")
                        .hasAnyRole(RoleConstants.ADMIN, RoleConstants.LIBRARIAN)

                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}
