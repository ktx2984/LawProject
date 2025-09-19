package com.smhrd.web.law.SocialLogin.Service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // kakao / naver
        Map<String, Object> attributes = new HashMap<>();

        String email = null;
        String nickname = null;

        if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            Map<String, Object> properties = oAuth2User.getAttribute("properties");

            email = (kakaoAccount != null && kakaoAccount.get("email") != null)
                    ? (String) kakaoAccount.get("email")
                    : "temp_email_kakao";

            nickname = (properties != null && properties.get("nickname") != null)
                    ? (String) properties.get("nickname")
                    : "temp_nickname_kakao";

        } else if ("naver".equals(registrationId)) {
            Map<String, Object> response = oAuth2User.getAttribute("response");

            email = (response != null && response.get("email") != null)
                    ? (String) response.get("email")
                    : "temp_email_naver";

            nickname = (response != null && response.get("nickname") != null)
                    ? (String) response.get("nickname")
                    : "temp_nickname_naver";
        }

        attributes.put("email", email);
        attributes.put("nickname", nickname);
        attributes.put("provider", registrationId);

        // ❗ 반드시 id 역할 key 존재
        return new DefaultOAuth2User(
                Collections.singleton(() -> "ROLE_USER"),
                attributes,
                "email" // attributes에 반드시 존재하는 key
        );
    }
}
