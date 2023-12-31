package com.itkon.spring6restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@Profile("!test")
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers(
                        antMatcher("/v3/api-docs**"),
                        antMatcher("/swagger-ui/**"),
                        antMatcher("/swagger-ui.html")
                )
                .permitAll()
                .anyRequest().authenticated()
                .and().oauth2ResourceServer().jwt();

        return http.build();
    }
}
