<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Booking Confirmation</title>
    <style>
        .center {
            margin: auto;
            width: 50%;
            border: 2px solid #32CD32;
            padding: 20px;
            font-family: Arial, sans-serif;
            text-align: center;
        }
        h2 {
            color: #32CD32;
        }
    </style>
</head>
<body>
    <div class="center">
        <h2>Booking Status</h2>

        <p>Booking ID: ${bookingId}</p>
        <p>Cab ID: ${cabId}</p>
        <p>Total Fare: ₹${fare}</p>
        <p>Status: ${status}</p>

        <br>
        <a href="${pageContext.request.contextPath}/book-cab">Book Another Cab</a> |
        <a href="${pageContext.request.contextPath}/user-dashboard">Back to Dashboard</a>
    </div>
</body>
</html>
