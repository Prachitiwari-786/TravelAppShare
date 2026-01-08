<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Bookings</title>
    <style>
        .center { margin: auto; width: 80%; border: 2px solid #4682B4; padding: 20px; font-family: Arial, sans-serif; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #4682B4; padding: 8px; text-align: center; }
        th { background-color: #4682B4; color: white; }
        h2 { text-align: center; }
    </style>
</head>
<body>
<div class="center">
    <h2>All Bookings</h2>

    <c:if test="${empty bookings}">
        <p style="text-align:center;">No bookings found.</p>
    </c:if>

    <c:if test="${not empty bookings}">
        <table>
            <tr>
                <th>Booking ID</th>
                <th>User ID</th>
                <th>Cab ID</th>
                <th>Total Fare</th>
                <th>Status</th>
            </tr>
            <c:forEach var="b" items="${bookings}">
                <tr>
                    <td>${b.id}</td>
                    <td>${b.userId}</td>
                    <td>${b.cabId}</td>
                    <td>₹${b.totalFare}</td>
                    <td>${b.status}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <div style="text-align:center; margin-top:20px;">
        <a href="${pageContext.request.contextPath}/admin/dashboard">Back to Dashboard</a>
    </div>
</div>
</body>
</html>
