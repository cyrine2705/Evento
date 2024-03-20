package com.app.evento.security;

import com.app.evento.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    //hedhi kolha secuirty o feha config taa swagger
    @Qualifier("userDetailsServiceImpl")
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests().requestMatchers("/actuator/**","/swagger-ui/**","/v3/api-docs",
                        "/api/auth/**",
                        "/v3/api-docs/swagger-config").anonymous()
                .requestMatchers(HttpMethod.POST  ,"/api/events/**","/api/guests/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET  ,"/api/events/**").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.DELETE,"/api/events/**","/api/guests/**","/api/workshops/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/api/workshops/**").hasAnyAuthority("ADMIN","AGENT")
                .requestMatchers(HttpMethod.PUT,"/api/events/**","/api/guests/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/guests/**","/api/workshops/**").hasAnyAuthority("ADMIN","AGENT")
                .anyRequest().authenticated()
                .and()
            //    .exceptionHandling(exception-> exception.accessDeniedHandler(accessDeniedHandler()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) ->
            response.sendRedirect("http://localhost:8111/api/auth/login");
        }

  
}
