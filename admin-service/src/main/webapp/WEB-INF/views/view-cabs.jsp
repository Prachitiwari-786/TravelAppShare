<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Cab Details</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f6f9; }
        .container { width: 80%; margin: 40px auto; background: #fff; border: 2px solid #4682B4;
                     border-radius: 8px; padding: 20px; box-shadow: 0px 4px 8px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #4682B4; }
        .actions-top { text-align: right; margin-bottom: 15px; }
        .actions-top a { background-color: #4682B4; color: white; padding: 8px 12px;
                         text-decoration: none; border-radius: 4px; }
        .actions-top a:hover { background-color: #315f86; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { border: 1px solid #4682B4; padding: 10px; text-align: center; }
        th { background-color: #4682B4; color: white; }
        .table-actions a { margin: 0 5px; color: #4682B4; text-decoration: none; font-weight: bold; }
        .table-actions a:hover { text-decoration: underline; }
        .back-link { text-align: center; margin-top: 20px; }
    </style>
</head>
<body>
<div class="container">
    <h2>All Cab Details</h2>

    <div class="actions-top">
        <a href="${pageContext.request.contextPath}/admin/cabs/add">Add New Cab</a>
    </div>

    <c:if test="${empty cabList}">
        <p style="text-align:center;">No cabs available.</p>
    </c:if>

    <c:if test="${not empty cabList}">
        <table>
            <tr>
                <th>ID</th>
                <th>Source</th>
                <th>Destination</th>
                <th>Type</th>
                <th>Kms</th>
                <th>Fare/Km</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="cab" items="${cabList}">
                <tr>
                    <td>${cab.id}</td>
                    <td>${cab.source}</td>
                    <td>${cab.destination}</td>
                    <td>${cab.type}</td>
                    <td>${cab.kms}</td>
                    <td>${cab.farePerKm}</td>
                    <td class="table-actions">
                        <a href="${pageContext.request.contextPath}/admin/cabs/edit/${cab.id}">Edit</a> |
                        <a href="${pageContext.request.contextPath}/admin/cabs/delete/${cab.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <div class="back-link">
        <a href="${pageContext.request.contextPath}/admin/dashboard">Back to Dashboard</a>
    </div>
</div>
</body>
</html>
