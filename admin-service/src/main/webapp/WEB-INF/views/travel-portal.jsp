<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Travel Portal</title>
    <style>
        body { font-family: Arial, sans-serif; background: linear-gradient(to right, #f4f6f9, #fff8e7); }
        .portal-container { width: 500px; margin: 80px auto; background: #fff; border: 2px solid #FFA500;
                            border-radius: 10px; padding: 40px; box-shadow: 0px 6px 12px rgba(0,0,0,0.1);
                            text-align: center; }
        h1 { color: #FFA500; margin-bottom: 30px; }
        .portal-buttons a { display: block; width: 80%; margin: 15px auto; padding: 14px; font-size: 18px;
                            background-color: #FFA500; color: white; border-radius: 6px; text-decoration: none;
                            font-weight: bold; }
        .portal-buttons a:hover { background-color: #cc8400; }
        .footer { margin-top: 30px; font-size: 14px; color: #666; }
    </style>
</head>
<body>
<div class="portal-container">
    <h1>Welcome to Travel Portal</h1>

    <div class="portal-buttons">
        <a href="${pageContext.request.contextPath}/admin/login-page">Admin Login</a>
        <a href="${pageContext.request.contextPath}/user-login">User Login</a>
    </div>

    <div class="footer">
        &copy; 2026 CabConnect Travel Portal. All rights reserved.
    </div>
</div>
</body>
</html>
