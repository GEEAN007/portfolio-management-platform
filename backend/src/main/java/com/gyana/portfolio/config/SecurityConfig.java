package com.gyana.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.gyana.portfolio.repository.UserRepository;

@Configuration
public class SecurityConfig {

@Bean
public JwtFilter jwtFilter(UserRepository userRepository) {
    return new JwtFilter(userRepository);
}

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

    http
        .csrf(csrf -> csrf.disable()) // 🔥 fully disable CSRF for JWT APIs
        .authorizeHttpRequests(auth -> auth
        .requestMatchers("/auth/**", "/h2-console/**").permitAll()
        .requestMatchers("/users/**").hasRole("ADMIN")
        .requestMatchers("/projects/**").authenticated()
        .anyRequest().authenticated()        )
        .headers(headers -> headers
            .frameOptions(frame -> frame.disable())
        )
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .httpBasic(httpBasic -> httpBasic.disable())
        .formLogin(form -> form.disable())
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
@Bean
public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
@Bean
public UserDetailsService userDetailsService() {
    return username -> null;
}
}