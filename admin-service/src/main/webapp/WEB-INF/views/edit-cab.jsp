<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.example.demo.entity.Cab" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Cab</title>
</head>
<body>

<h2>Edit Cab</h2>

<%
    Cab cab = (Cab) request.getAttribute("cab");
%>

<form action="${pageContext.request.contextPath}/admin/edit-page/<%= cab.getId() %>" method="post">
    Source: <input type="text" name="source" value="<%= cab.getSource() %>"><br><br>
    Destination: <input type="text" name="destination" value="<%= cab.getDestination() %>"><br><br>
    Type: <input type="text" name="type" value="<%= cab.getType() %>"><br><br>
    Kms: <input type="number" name="kms" value="<%= cab.getKms() %>"><br><br>
    Fare/Km: <input type="number" step="0.01" name="farePerKm" value="<%= cab.getFarePerKm() %>"><br><br>
    <button type="submit">Update Cab</button>
</form>


<br>
<a href="${pageContext.request.contextPath}/admin/view-cabs">Back</a>

</body>
</html>