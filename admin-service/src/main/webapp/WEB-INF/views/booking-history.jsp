<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Booking History</title>
    <style>
        .center {
            margin: auto;
            width: 70%;
            border: 2px solid #4682B4;
            padding: 20px;
            font-family: Arial, sans-serif;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #4682B4;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #4682B4;
            color: white;
        }
    </style>
</head>
<body>
<div class="center">
    <h2>${loggedUser.username}'s Booking History</h2>

    <c:if test="${empty bookings}">
        <p>No bookings found.</p>
    </c:if>

    <c:if test="${not empty bookings}">
        <table>
            <tr>
                <th>Booking ID</th>
                <th>Cab ID</th>
                <th>Total Fare</th>
                <th>Status</th>
            </tr>
            <c:forEach var="b" items="${bookings}">
                <tr>
                    <td>${b.id}</td>
                    <td>${b.cabId}</td>
                    <td>₹${b.totalFare}</td>
                    <td>${b.status}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <br>
    <a href="${pageContext.request.contextPath}/user-dashboard">Back to Dashboard</a>
</div>
</body>
</html>
