package com.smhrd.web.config;

import com.smhrd.web.law.SocialLogin.Service.SocialLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정 클래스
 * - 인증/인가(접근 권한) 규칙 정의
 * - 소셜 로그인 설정
 * - 비밀번호 암호화 방식 정의
 */
@Configuration               // 스프링 설정 클래스임을 명시
@EnableWebSecurity           // Spring Security 활성화
public class SecurityConfig {

    // 소셜 로그인 시 사용자 정보를 가공하는 서비스
    @Autowired
    private SocialLoginService socialLoginService;

    /**
     * 비밀번호 암호화 객체를 Bean으로 등록
     * - 회원가입/로그인 시 BCrypt 해시 알고리즘 사용
     */
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security 필터 체인 설정
     * - 요청 URL 별 접근 권한
     * - 로그인/로그아웃 및 OAuth2 로그인 처리
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 보안 설정 해제 (개발 단계에서 주로 사용)
        http.csrf(csrf -> csrf.disable());

        // 요청 URL 별 접근 권한 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/user/**").authenticated()               // /user/** → 로그인 필요
                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // /manager/** → MANAGER, ADMIN만 접근 가능
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")           // /admin/** → ADMIN만 접근 가능
                .requestMatchers("/oauth2/**", "/login/**").permitAll()     // 소셜 로그인 콜백 경로는 모두 허용
                .anyRequest().permitAll()                                   // 그 외 모든 요청은 허용
        );

        // OAuth2 로그인 설정
        http.oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/home", true) // 로그인 성공 시 /home 페이지로 이동
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(socialLoginService) // 사용자 정보 가공하는 서비스 연결
                )
        );

        // 로그아웃 설정
        http.logout(logout ->
                logout.logoutSuccessUrl("/")  // 로그아웃 성공 시 메인("/")으로 이동
                        .permitAll()          // 누구나 로그아웃 가능
        );

        return http.build();
    }
}
