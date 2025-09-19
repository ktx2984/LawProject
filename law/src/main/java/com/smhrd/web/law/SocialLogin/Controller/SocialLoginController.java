package com.smhrd.web.law.SocialLogin.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Controller
public class SocialLoginController {

    @GetMapping("/")
    public String gohome() {
        return "main";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            Map<String, Object> kakaoAccount = principal.getAttribute("kakao_account");
            Map<String, Object> properties = principal.getAttribute("properties");

            String email = kakaoAccount != null ? (String) kakaoAccount.get("email") : "이메일 없음";
            String nickname = properties != null ? (String) properties.get("nickname") : "닉네임 없음";

            model.addAttribute("email", email);
            model.addAttribute("nickname", nickname);
        }
        return "home";
    }
}
