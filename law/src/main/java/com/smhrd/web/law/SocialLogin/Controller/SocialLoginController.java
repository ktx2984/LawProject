package com.smhrd.web.law.SocialLogin.Controller;

/**
 * 수정 내역
 * gohome() 메서드명을 goMain()으로 변경
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * 소셜 로그인 이후 사용자 정보를 화면(View)에 전달하는 컨트롤러
 */
@Controller
public class SocialLoginController {

    /**
     * 루트("/")로 접근했을 때 main.jsp 뷰 반환
     */
    @GetMapping("/")
    public String goMain() {
        return "main"; // main 페이지로 이동
    }

    /**
     * /home URL 접근 시 로그인된 사용자 정보 확인
     * @param principal 현재 로그인된 사용자 (Spring Security가 주입)
     * @param model 화면(View)으로 데이터 전달
     * @return home.jsp (또는 home.html) 뷰
     */
    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            // ✅ 로그인된 경우 사용자 정보 추출

            // provider (로그인 제공자: kakao/naver 등)
            String provider = principal.getAttribute("provider") != null
                    ? principal.getAttribute("provider")
                    : "unknown";

            // nickname (없으면 name 속성 사용, 그것도 없으면 기본 문구)
            String nickname = principal.getAttribute("nickname") != null
                    ? principal.getAttribute("nickname")
                    : (principal.getAttribute("name") != null
                        ? principal.getAttribute("name") 
                        : "닉네임 없음");

            // email (없으면 기본 문구)
            String email = principal.getAttribute("email") != null
                    ? principal.getAttribute("email")
                    : "이메일 없음";

            // Model 객체에 사용자 정보 담아서 View로 전달
            model.addAttribute("provider", provider);
            model.addAttribute("nickname", nickname);
            model.addAttribute("email", email);

        } else {
            // ❌ 로그인하지 않은 경우 기본값 설정
            model.addAttribute("provider", "guest");
            model.addAttribute("nickname", "손님");
            model.addAttribute("email", "이메일 없음");
        }

        // home.jsp (또는 home.html)로 이동
        return "home";
    }

}
