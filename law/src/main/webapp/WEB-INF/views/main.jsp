<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>소셜 로그인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        h1 {
            margin-bottom: 30px;
        }

        .login-box {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            text-align: center;
            width: 300px;
        }

        .social-btn {
            display: block;
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            text-align: center;
            font-size: 16px;
            color: #fff;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
        }

        .google { background-color: #db4437; }
        .naver { background-color: #03c75a; }
        .kakao { background-color: #fee500; color: #3c1e1e; }

        .social-btn:hover {
            opacity: 0.85;
        }

        .divider {
            margin: 20px 0;
            font-weight: bold;
            color: #666;
        }

        /* 일반 로그인 */
        .form-login input[type="text"],
        .form-login input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        .form-login button {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
        }

        .form-login button:hover {
            opacity: 0.9;
        }

        .form-login small {
            display: block;
            margin-top: 10px;
            color: #888;
        }
    </style>
</head>
<body>
    <div class="login-box">
        <h1>소셜 로그인</h1>

        <!-- 소셜 로그인 버튼 -->
        <a class="social-btn google" href="/law/oauth2/authorization/google">Google 로그인</a>
        <a class="social-btn naver" href="/law/oauth2/authorization/naver">Naver 로그인</a>
        <a class="social-btn kakao" href="/law/oauth2/authorization/kakao">Kakao 로그인</a>

        <div class="divider">OR</div>

        <!-- 일반 로그인 폼 -->
        <form class="form-login" action="/login" method="post">
            <input type="text" name="username" placeholder="아이디" required>
            <input type="password" name="password" placeholder="비밀번호" required>
            <button type="submit">로그인</button>
            <small>아이디/비밀번호 로그인</small>
        </form>
    </div>
</body>
</html>
