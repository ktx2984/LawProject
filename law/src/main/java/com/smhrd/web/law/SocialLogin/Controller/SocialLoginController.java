package com.smhrd.web.law.SocialLogin.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Controller
public class SocialLoginController {

    @GetMapping("/")
    public String gohome() {
        return "main";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            // provider
            String provider = principal.getAttribute("provider") != null
                    ? principal.getAttribute("provider")
                    : "unknown";

            // nickname
            String nickname = principal.getAttribute("nickname") != null
                    ? principal.getAttribute("nickname")
                    : (principal.getAttribute("name") != null
                        ? principal.getAttribute("name") 
                        : "닉네임 없음");

            // email
            String email = principal.getAttribute("email") != null
                    ? principal.getAttribute("email")
                    : "이메일 없음";

            // model에 추가
            model.addAttribute("provider", provider);
            model.addAttribute("nickname", nickname);
            model.addAttribute("email", email);
        } else {
            // 로그인 안 된 경우 기본값
            model.addAttribute("provider", "guest");
            model.addAttribute("nickname", "손님");
            model.addAttribute("email", "이메일 없음");
        }

        return "home";
    }

}
