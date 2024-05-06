package org.example.food.config;

import lombok.RequiredArgsConstructor;
import org.example.food.security.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/", " /register", "/login", "/terms").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2.loginPage("/login");
                    oauth2.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService));
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/");
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"));
                })
                .build();
    }

}
