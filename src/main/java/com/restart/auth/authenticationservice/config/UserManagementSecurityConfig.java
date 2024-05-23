package com.restart.auth.authenticationservice.config;

import com.restart.auth.authenticationservice.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
public class UserManagementSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter((new KeycloakRoleConverter()));
        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request ->
                                {
                                    CorsConfiguration config = new CorsConfiguration();
                                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                                    config.setAllowedMethods(Collections.singletonList("*"));
                                    config.setAllowCredentials(true);
                                    config.setAllowedHeaders(Collections.singletonList("*"));
                                    config.setExposedHeaders(List.of("Authorization"));
                                    config.setMaxAge(3600L);
                                    return config;
                                }
                        )
                )
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.csrfTokenRequestHandler(requestHandler)
                .ignoringRequestMatchers("/register")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class).

        //httpSecurity.securityContext(httpSecuritySecurityContextConfigurer -> httpSecuritySecurityContextConfigurer.requireExplicitSave(false))
          //              .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                //uncomment below line once UI gets integrated
        /*httpSecurity.*/authorizeHttpRequests(auth -> auth.requestMatchers("/test", "/test1", "/register").hasAnyAuthority("user","VIEWACCOUNT")
                        //.requestMatchers("/employee").hasAnyAuthority("user", "USER", "VIEWCARD").anyRequest().permitAll())
                        .requestMatchers("/employee").hasAnyRole("user", "USER", "VIEWCARD").anyRequest().permitAll())
        //httpSecurity.
        .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));
        //httpSecurity.formLogin(Customizer.withDefaults());
        //httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
