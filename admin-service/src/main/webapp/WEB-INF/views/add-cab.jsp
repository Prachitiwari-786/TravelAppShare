<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Cab</title>
</head>
<body>

<h2>Add New Cab</h2>

<form action="${pageContext.request.contextPath}/admin/add-cab" method="post">
    Source: <input type="text" name="source"><br><br>
    Destination: <input type="text" name="destination"><br><br>
    Type: <input type="text" name="type"><br><br>
    Kms: <input type="number" name="kms"><br><br>
    Fare/Km: <input type="number" step="0.01" name="farePerKm"><br><br>

    <button type="submit">Save Cab</button>
</form>

<br>
<a href="${pageContext.request.contextPath}/admin/view-cabs">Back</a>

</body>
</html>