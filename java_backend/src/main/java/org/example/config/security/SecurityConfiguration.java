package org.example.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    //private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/rest-api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/genres").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/genres").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/genres").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/genres").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/games").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/games").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/games").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/games").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}