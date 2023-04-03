package ru.tsu.hits.creditservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import ru.tsu.hits.creditservice.entity.Role;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.POST, "/api/tariffs").hasRole(Role.EMPLOYEE.name());
            auth.requestMatchers(HttpMethod.GET, "/api/tariffs").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/api/ratings", "/api/ratings/**").hasRole(Role.EMPLOYEE.name());
            auth.requestMatchers("/swagger-ui", "/swagger-ui/**", "/v3/api-docs/**").permitAll();
            auth.anyRequest().authenticated();
        });
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}