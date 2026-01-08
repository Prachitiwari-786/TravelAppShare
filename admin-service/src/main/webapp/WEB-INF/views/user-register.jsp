<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
        }
        .register-container {
            width: 450px;
            margin: 60px auto;
            background: #fff;
            border: 2px solid #4682B4;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0px 4px 8px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            color: #4682B4;
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 12px;
        }
        input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            width: 100%;
            margin-top: 20px;
            background-color: #4682B4;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #315f86;
        }
        .links {
            text-align: center;
            margin-top: 15px;
        }
        .links a {
            color: #4682B4;
            text-decoration: none;
            margin: 0 10px;
        }
        .links a:hover {
            text-decoration: underline;
        }
        .message {
            text-align: center;
            color: green;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h2>User Registration</h2>

    <!-- Success or error message -->
    <c:if test="${not empty message}">
        <p class="message">${message}</p>
    </c:if>

    <form action="/register-user" method="post">
        <label>Username:</label>
        <input type="text" name="username" required>

        <label>Email:</label>
        <input type="email" name="email" required>

        <label>Password:</label>
        <input type="password" name="password" required>

        <label>Confirm Password:</label>
        <input type="password" name="confirmPassword" required>

        <button type="submit">Register</button>
    </form>

    <div class="links">
        <a href="/login-user">Already registered? Login here</a> |
        <a href="/">Back to Home</a>
    </div>
</div>
</body>
</html>
