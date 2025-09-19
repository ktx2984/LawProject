package com.smhrd.web.config;

import com.smhrd.web.law.SocialLogin.Service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService; // 이미 @Service로 등록되어 있음

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                .requestMatchers("/oauth2/**", "/login/**").permitAll() // 콜백 경로 허용
                .anyRequest().permitAll()
        );

        http.oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/home", true) // 로그인 성공 시 이동
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(customOAuth2UserService) // @Autowired로 주입된 커스텀 서비스 사용
                )
        );

        http.logout(logout ->
                logout.logoutSuccessUrl("/")
                        .permitAll()
        );

        return http.build();
    }
}
