/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hardworker.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.hardworker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 * @author Kain
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    public static final String UNKNOWN_USERNAME = "<unknown>";
    
    @Bean
    public UserDetailsService userDetailsService(){ return new UserDetailsServiceImpl(); }
    
    @Bean
    public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList(
                RequestMethod.GET.name()
                , RequestMethod.POST.name()
                , RequestMethod.PUT.name()
                , RequestMethod.DELETE.name()
        ));
        corsConfiguration.setExposedHeaders(Arrays.asList(
                HttpHeaders.CONTENT_TYPE
                , HttpHeaders.CONTENT_DISPOSITION
                , HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN
                , HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS
        ));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowCredentials(Boolean.TRUE);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                .httpBasic()//.disable()
                .and()
                    .cors()
                .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                    .authorizeRequests()
                        .antMatchers("/users", "/user", "/roles", "/role", "/projects", "/project", "/tables", "/table", "/profile")
                        .hasAnyRole("ADMIN", "USER")
                        .anyRequest().denyAll()
                .and()
                    .formLogin().permitAll()
                    .and()
                    .logout().permitAll()
//                .and()
  //                  .cors().configurationSource(corsConfigurationSource())
                .and().build();
    }

    public static CustomUserDetails getCustomUserDetails(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            return principal instanceof CustomUserDetails
                    ? (CustomUserDetails) principal
                    : new CustomUserDetails(
                            authentication.getDetails() instanceof CustomUserDetails
                                ? ((User) authentication.getDetails())
                                : null);
        }
        return null;
    }
    public static String getUserName(){
        return Optional.ofNullable(getCustomUserDetails())
                .map(CustomUserDetails::getUsername)
                .orElse(UNKNOWN_USERNAME);
    }
}
