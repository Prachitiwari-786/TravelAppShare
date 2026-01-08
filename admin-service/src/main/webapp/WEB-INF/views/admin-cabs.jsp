<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Cabs</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f6f9;
    }
    .container {
        width: 80%;
        margin: 40px auto;
        background: #fff;
        border: 2px solid #4682B4;
        border-radius: 8px;
        padding: 20px;
        box-shadow: 0px 4px 8px rgba(0,0,0,0.1);
    }
    h2 {
        text-align: center;
        color: #4682B4;
    }
    .actions-top {
        text-align: right;
        margin-bottom: 15px;
    }
    .actions-top a {
        background-color: #4682B4;
        color: white;
        padding: 8px 12px;
        text-decoration: none;
        border-radius: 4px;
    }
    .actions-top a:hover {
        background-color: #315f86;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 15px;
    }
    th, td {
        border: 1px solid #4682B4;
        padding: 10px;
        text-align: center;
    }
    th {
        background-color: #4682B4;
        color: white;
    }
    .table-actions a {
        margin: 0 5px;
        color: #4682B4;
        text-decoration: none;
        font-weight: bold;
    }
    .table-actions a:hover {
        text-decoration: underline;
    }
    .back-link {
        display: block;
        text-align: center;
        margin-top: 20px;
    }
    .back-link a {
        color: #4682B4;
        text-decoration: none;
    }
    .back-link a:hover {
        text-decoration: underline;
    }
</style>
</head>
<body>
<div class="container">
    <h2>All Cab Details</h2>

    <div class="actions-top">
        <a href="${pageContext.request.contextPath}/admin/cabs/add">Add New Cab</a>
    </div>

    <c:if test="${empty cabs}">
        <p style="text-align:center;">No cabs available.</p>
    </c:if>

    <c:if test="${not empty cabs}">
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
            <c:forEach var="c" items="${cabList}">
                <tr>
                    <td>${c.id}</td>
                    <td>${c.source}</td>
                    <td>${c.destination}</td>
                    <td>${c.type}</td>
                    <td>${c.noOfKms}</td>
                    <td>${c.farePerKm}</td>
                    <td class="table-actions">
                        <a href="${pageContext.request.contextPath}/admin/cabs/edit/${c.id}">Edit</a> |
                        <a href="${pageContext.request.contextPath}/admin/cabs/delete/${c.id}">Delete</a>
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
