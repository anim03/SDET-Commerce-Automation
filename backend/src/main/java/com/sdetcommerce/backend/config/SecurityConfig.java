package com.sdetcommerce.backend.config;

import com.sdetcommerce.backend.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        /*
                         * Public authentication endpoints
                         */
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/error"
                        )
                        .permitAll()

                        /*
                         * Swagger / OpenAPI endpoints
                         */
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        )
                        .permitAll()

                        /*
                         * Product READ:
                         * Any authenticated user
                         */
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/products/**"
                        )
                        .authenticated()

                        /*
                         * Product CREATE:
                         * ADMIN only
                         */
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/products/**"
                        )
                        .hasRole("ADMIN")

                        /*
                         * Product UPDATE:
                         * ADMIN only
                         */
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/products/**"
                        )
                        .hasRole("ADMIN")

                        /*
                         * Product DELETE:
                         * ADMIN only
                         */
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/products/**"
                        )
                        .hasRole("ADMIN")

                        /*
                         * Cart, Orders, Payments etc.
                         * Authentication required
                         */
                        .anyRequest()
                        .authenticated()
                )

                .exceptionHandling(exception ->
                        exception

                                /*
                                 * No token / invalid token
                                 * -> 401 Unauthorized
                                 */
                                .authenticationEntryPoint(
                                        (request,
                                         response,
                                         authException) -> {

                                            response.setStatus(
                                                    HttpServletResponse
                                                            .SC_UNAUTHORIZED
                                            );
                                        }
                                )

                                /*
                                 * Valid JWT but insufficient role
                                 * -> 403 Forbidden
                                 */
                                .accessDeniedHandler(
                                        (request,
                                         response,
                                         accessDeniedException) -> {

                                            response.setStatus(
                                                    HttpServletResponse
                                                            .SC_FORBIDDEN
                                            );
                                        }
                                )
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}