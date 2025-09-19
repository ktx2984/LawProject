<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>카카오 로그인 - 홈</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            text-align: center;
        }
        h1 {
            color: #ffeb00;
        }
        p {
            font-size: 18px;
            margin: 10px 0;
        }
        .logout {
            margin-top: 20px;
            display: inline-block;
            padding: 10px 20px;
            text-decoration: none;
            background-color: #ffeb00;
            color: black;
            border-radius: 5px;
            font-weight: bold;
        }
        .logout:hover {
            background-color: #ffd700;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>카카오 로그인 성공!</h1>

        <p>닉네임: <span id="nickname">${nickname}</span></p>
        <p>이메일: <span id="email">${email}</span></p>

        <a href="/logout" class="logout">로그아웃</a>
    </div>

    <!-- JSP 환경이면 ${nickname}, ${email} 그대로 사용 가능 -->
</body>
</html>
