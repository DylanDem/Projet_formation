package com.accenture.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html").permitAll()
                        .requestMatchers("/clients/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/clients/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/clients/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/clients/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/clients/**").hasAnyRole("ADMIN", "SUPERADMIN")
                        .requestMatchers("/admins/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admins/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admins/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/admins/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admins/**").hasAnyRole("ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/motorbikes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/motorbikes/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/motorbikes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/motorbikes/**").hasAnyRole("ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/cars/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cars/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/cars/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/cars/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/vehicles/**").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select mail, password, 1 from clients where email = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select mail, role from clients where email = ?");
        return jdbcUserDetailsManager;
    }
}
