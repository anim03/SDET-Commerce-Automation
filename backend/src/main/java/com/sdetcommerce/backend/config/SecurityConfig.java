package com.sdetcommerce.backend.config;

import com.sdetcommerce.backend.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                /*
                 * Enable centralized CORS configuration.
                 *
                 * React frontend:
                 * http://localhost:5173
                 */
                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )

                /*
                 * CSRF is disabled because this application
                 * uses stateless JWT authentication.
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * No server-side HTTP session.
                 */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        /*
                         * Browser CORS preflight requests.
                         */
                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        )
                        .permitAll()

                        /*
                         * Public authentication endpoints.
                         */
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/error"
                        )
                        .permitAll()

                        /*
                         * Swagger / OpenAPI endpoints.
                         */
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        )
                        .permitAll()

                        /*
                         * Product READ:
                         * Any authenticated user.
                         */
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/products/**"
                        )
                        .authenticated()

                        /*
                         * Product CREATE:
                         * ADMIN only.
                         */
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/products/**"
                        )
                        .hasRole("ADMIN")

                        /*
                         * Product UPDATE:
                         * ADMIN only.
                         */
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/products/**"
                        )
                        .hasRole("ADMIN")

                        /*
                         * Product DELETE:
                         * ADMIN only.
                         */
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/products/**"
                        )
                        .hasRole("ADMIN")

                        /*
                         * Cart, Orders, Payments, Profile etc.
                         * Authentication required.
                         */
                        .anyRequest()
                        .authenticated()
                )

                .exceptionHandling(exception ->
                        exception

                                /*
                                 * Missing / invalid JWT
                                 * -> 401 Unauthorized
                                 */
                                .authenticationEntryPoint(
                                        (
                                                request,
                                                response,
                                                authException
                                        ) -> {

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
                                        (
                                                request,
                                                response,
                                                accessDeniedException
                                        ) -> {

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

    /*
     * CORS configuration for the React development frontend.
     *
     * This allows the browser application running on
     * localhost:5173 to communicate with Spring Boot
     * running on localhost:8080.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:5173"
                )
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type"
                )
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}
