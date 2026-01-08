<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book a Cab</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
        }
        .booking-container {
            width: 500px;
            margin: 60px auto;
            background: #fff;
            border: 2px solid #FFA500;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0px 4px 8px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            color: #FFA500;
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 12px;
        }
        select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            width: 100%;
            margin-top: 20px;
            background-color: #FFA500;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #cc8400;
        }
        .links {
            text-align: center;
            margin-top: 15px;
        }
        .links a {
            color: #FFA500;
            text-decoration: none;
            margin: 0 10px;
        }
        .links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<%
    if (session.getAttribute("loggedUser") == null) {
        response.sendRedirect("/user-login");
        return;
    }
%>
<body>
<div class="booking-container">
    <h2>Book a Cab</h2>

    <!-- Form points to fare calculation -->
    <form action="${pageContext.request.contextPath}/booking/fare" method="get">
        <label>Source:</label>
        <select name="source" required>
            <option value="">--Select--</option>
            <option value="Pune">Pune</option>
            <option value="Mumbai">Mumbai</option>
            <option value="Nashik">Nashik</option>
            <option value="Kolhapur">Kolhapur</option>
        </select>

        <label>Destination:</label>
        <select name="destination" required>
            <option value="">--Select--</option>
            <option value="Mumbai">Mumbai</option>
            <option value="Pune">Pune</option>
            <option value="Nashik">Nashik</option>
            <option value="Kolhapur">Kolhapur</option>
        </select>

        <label>Cab Type:</label>
        <select name="type" required>
            <option value="">--Select--</option>
            <option value="Sedan">Sedan</option>
            <option value="SUV">SUV</option>
            <option value="Hatchback">Hatchback</option>
        </select>

        <button type="submit">Check Fare</button>
    </form>

    <div class="links">
        <a href="${pageContext.request.contextPath}/user-dashboard">Back to Dashboard</a> |
        <a href="${pageContext.request.contextPath}/">Home</a>
    </div>
</div>
</body>
</html>
