package com.smhrd.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * .env 파일에 정의된 OAuth Client ID/Secret을 로딩하는 설정 클래스
 */
@Component
public class EnvConfig {

    // ===== Google =====
    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String googleClientSecret;

    // ===== Naver =====
    @Value("${NAVER_CLIENT_ID}")
    private String naverClientId;

    @Value("${NAVER_CLIENT_SECRET}")
    private String naverClientSecret;

    // ===== Kakao =====
    @Value("${KAKAO_CLIENT_ID}")
    private String kakaoClientId;

    @Value("${KAKAO_CLIENT_SECRET}")
    private String kakaoClientSecret;

    // === Getter ===
    public String getGoogleClientId() {
        return googleClientId;
    }

    public String getGoogleClientSecret() {
        return googleClientSecret;
    }

    public String getNaverClientId() {
        return naverClientId;
    }

    public String getNaverClientSecret() {
        return naverClientSecret;
    }

    public String getKakaoClientId() {
        return kakaoClientId;
    }

    public String getKakaoClientSecret() {
        return kakaoClientSecret;
    }
}
