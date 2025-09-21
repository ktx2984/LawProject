package com.smhrd.web.law.SocialLogin.Service;

/**
 * 수정 내역
 * CustomOAuthUserService라는 클래스명을 SocialLoginService라는 이름으로 변경함
 * 
 */

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 소셜 로그인(Kakao, Naver 등) 시 사용자 정보를 불러와
 * 애플리케이션에서 사용할 수 있는 형태로 가공하는 서비스 클래스
 */
@Service
public class SocialLoginService extends DefaultOAuth2UserService {

    /**
     * OAuth2User 정보를 불러오는 메서드
     * @param userRequest 소셜 로그인 요청 객체 (어떤 Provider인지 포함)
     * @return OAuth2User 가공된 사용자 정보
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // 부모 클래스의 loadUser 실행 → Provider(카카오/네이버)에서 사용자 정보 받아옴
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 어떤 Provider(kakao/naver)인지 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 최종 반환할 사용자 정보를 담을 Map
        Map<String, Object> attributes = new HashMap<>();

        // 사용자 이메일/닉네임 기본값
        String email = null;
        String nickname = null;

        // 카카오 로그인 처리
        if ("kakao".equals(registrationId)) {
            // kakao_account, properties 에서 필요한 정보 추출
            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            Map<String, Object> properties = oAuth2User.getAttribute("properties");

            // 이메일이 존재하면 사용, 없으면 임시 값
            email = (kakaoAccount != null && kakaoAccount.get("email") != null)
                    ? (String) kakaoAccount.get("email")
                    : "temp_email_kakao";

            // 닉네임이 존재하면 사용, 없으면 임시 값
            nickname = (properties != null && properties.get("nickname") != null)
                    ? (String) properties.get("nickname")
                    : "temp_nickname_kakao";

        // 네이버 로그인 처리
        } else if ("naver".equals(registrationId)) {
            // response 객체 안에 email, nickname 존재
            Map<String, Object> response = oAuth2User.getAttribute("response");

            email = (response != null && response.get("email") != null)
                    ? (String) response.get("email")
                    : "temp_email_naver";

            nickname = (response != null && response.get("nickname") != null)
                    ? (String) response.get("nickname")
                    : "temp_nickname_naver";
        }

        // 공통 속성값 저장
        attributes.put("email", email);
        attributes.put("nickname", nickname);
        attributes.put("provider", registrationId);

        // DefaultOAuth2User는 'id 역할을 하는 key'가 반드시 있어야 함 → 여기서는 "email"
        return new DefaultOAuth2User(
                Collections.singleton(() -> "ROLE_USER"), // 기본 권한 부여
                attributes, // 사용자 속성
                "email"     // 사용자 식별 key (attributes에 반드시 존재해야 함)
        );
    }
}
