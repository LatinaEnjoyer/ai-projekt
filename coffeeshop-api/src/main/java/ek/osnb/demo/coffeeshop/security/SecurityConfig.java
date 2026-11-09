package ek.osnb.demo.coffeeshop.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(csrf -> csrf.spa())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/carts", "/api/carts/**", "/api/products","/api/products/**", "/api/categories").permitAll()
                        .requestMatchers("/api/register","/api/login").permitAll()
                        .requestMatchers("/api/logout", "/api/me", "/api/assistant/chat").authenticated()
                        .requestMatchers("/api/customers/me", "/api/orders", "/api/orders/{id}/cancel").hasRole("CUSTOMER")
                        .requestMatchers("/api/orders/{id}").hasAnyRole("STAFF", "CUSTOMER")
                        .requestMatchers("/api/orders/{id}/ship", "/api/staff/orders").hasRole("STAFF")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/api/login")
                        .successHandler((_,res,_) -> res.setStatus(HttpServletResponse.SC_NO_CONTENT))
                        .failureHandler((_,res,_) -> res.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((_,res,_) -> res.setStatus(HttpServletResponse.SC_NO_CONTENT))
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(
                                (_,res,ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
