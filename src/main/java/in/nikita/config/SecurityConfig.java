package in.nikita.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.nikita.filters.JWTFilters;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilters jwtFilters;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                // Public URLs
                .requestMatchers(
                    "/index",
                    "/login",
                    "/register",
                    "/logout",      
                    "/styles/**",
                    "/images/**",
                    "/js/**",
                    "/saveMessage"
                ).permitAll()
                // Role-based access
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/customer/**").hasAuthority("ROLE_USER")
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            // Add JWT filter before Spring Security authentication
            .addFilterBefore(jwtFilters, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
