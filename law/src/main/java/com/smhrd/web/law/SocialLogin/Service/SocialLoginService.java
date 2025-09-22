package com.smhrd.web.law.SocialLogin.Service;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 소셜 로그인(Kakao, Naver 등) 시 사용자 정보를 불러와
 * 애플리케이션에서 사용할 수 있는 형태로 가공하는 서비스 클래스
 */

@Service
public class SocialLoginService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    /**
     * OAuth2User 정보를 불러오는 메서드
     * @param userRequest 소셜 로그인 요청 객체 (어떤 Provider인지 포함)
     * @return OAuth2User 가공된 사용자 정보
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google / kakao / naver

        Map<String, Object> attributes = new HashMap<>();
        String id = null;
        String email = null;
        String nickname = null;

        if ("naver".equals(registrationId)) {
            // 네이버는 직접 API 호출
            OAuth2AccessToken accessToken = userRequest.getAccessToken();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken.getTokenValue());
            HttpEntity<String> entity = new HttpEntity<>("", headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://openapi.naver.com/v1/nid/me",
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            Map<String, Object> res = (Map<String, Object>) responseBody.get("response");

            id = (String) res.get("id");
            email = (String) res.get("email");
            nickname = (String) res.get("nickname");


            if (email == null) email = "temp_email_naver";
            if (nickname == null) nickname = "temp_nickname_naver";

        } else {
            // 카카오, 구글는 super.loadUser 사용
            DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            if ("kakao".equals(registrationId)) {
                Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
                Map<String, Object> properties = oAuth2User.getAttribute("properties");

                email = (kakaoAccount != null && kakaoAccount.get("email") != null)
                        ? (String) kakaoAccount.get("email")
                        : "temp_email_kakao";

                nickname = (properties != null && properties.get("nickname") != null)
                        ? (String) properties.get("nickname")
                        : "temp_nickname_kakao";

                id = email; // id 역할로 email 사용

            } else if ("google".equals(registrationId)) {
                email = oAuth2User.getAttribute("email");
                nickname = oAuth2User.getAttribute("name");

                if (email == null) email = "temp_email_google";
                if (nickname == null) nickname = "temp_nickname_google";

                id = email; // id 역할로 email 사용
            }
        }


        attributes.put("id", id);

        attributes.put("email", email);
        attributes.put("nickname", nickname);
        attributes.put("provider", registrationId);


        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "id" // PK 역할

        );
    }
}
