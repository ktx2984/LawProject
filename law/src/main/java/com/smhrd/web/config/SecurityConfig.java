package com.smhrd.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 비활성화
        http.csrf(csrf -> csrf.disable());

        // URL 권한 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                .requestMatchers("/oauth2/**").permitAll()
                .anyRequest().permitAll()
        );

        /*        // 기존 Form 로그인 설정
        http.formLogin(form -> form
                .loginPage("/")
                .permitAll()
                .defaultSuccessUrl("/home", true) 
        );
        */
        // OAuth2 로그인 설정 추가
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/")           // 소셜 로그인 페이지
                .defaultSuccessUrl("/home", true)        // 로그인 성공 시 이동
        );

        // 로그아웃 설정
        http.logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
        );

        return http.build();
    }
}
