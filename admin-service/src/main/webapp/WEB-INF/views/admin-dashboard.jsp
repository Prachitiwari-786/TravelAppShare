<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f6f9; }
        .dashboard-container { width: 600px; margin: 60px auto; background: #fff; border: 2px solid #4682B4;
                               border-radius: 8px; padding: 30px; box-shadow: 0px 4px 8px rgba(0,0,0,0.1);
                               text-align: center; }
        h2 { color: #4682B4; margin-bottom: 30px; }
        .dashboard-links a { display: block; margin: 12px auto; width: 80%; background-color: #4682B4;
                             color: white; padding: 12px; text-decoration: none; border-radius: 4px;
                             font-size: 16px; font-weight: bold; }
        .dashboard-links a:hover { background-color: #315f86; }
        .back-link { margin-top: 20px; }
        .back-link a { color: #4682B4; text-decoration: none; }
        .back-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<div class="dashboard-container">
    <h2>Admin Dashboard</h2>

    <div class="dashboard-links">
        <a href="${pageContext.request.contextPath}/admin/cab-list">View All Cabs</a>
        <a href="${pageContext.request.contextPath}/admin/cabs/add">Add New Cab</a>
        <a href="${pageContext.request.contextPath}/admin/bookings">View All Bookings</a>
        <a href="${pageContext.request.contextPath}/admin/users">Manage Users</a>
    </div>

    <div class="back-link">
        <a href="${pageContext.request.contextPath}/admin/portal">Back to Home</a>
    </div>
</div>
</body>
</html>
