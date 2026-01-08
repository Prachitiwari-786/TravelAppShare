<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard</title>
    <style>
        .center { margin: auto; width: 50%; border: 2px solid #4682B4; padding: 20px;
                  font-family: Arial, sans-serif; text-align: center; }
        a { color: #4682B4; text-decoration: none; font-weight: bold; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<div class="center">
    <h2>Welcome to your Dashboard</h2>
    <p>Hello, ${loggedUser.username}!</p>

    <a href="${pageContext.request.contextPath}/book-cab">Book a Cab</a><br><br>
    <a href="${pageContext.request.contextPath}/booking/history">View My Bookings</a><br><br>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>
</body>
</html>
