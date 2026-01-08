<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Manage Users</title>
<style>
table {
	width: 80%;
	border-collapse: collapse;
	margin: 20px auto;
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

h2 {
	text-align: center;
}
</style>
</head>
<body>
	<h2>All Users</h2>

	<c:if test="${empty users}">
		<p style="text-align: center;">No users found.</p>
	</c:if>

	<c:if test="${not empty users}">
		<table>
			<tr>
				<th>ID</th>
				<th>Username</th>
				<th>Email</th>
				<th>Role</th>
				<th>Actions</th>
			</tr>
			<c:forEach var="u" items="${users}">
				<tr>
					<td>${u.id}</td>
					<td>${u.username}</td>
					<td>${u.email}</td>
					<td>${u.role}</td>
					<td><a
						href="${pageContext.request.contextPath}/admin/users/edit/${u.id}">Edit</a>
						<a
						href="${pageContext.request.contextPath}/admin/users/delete/${u.id}">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<div style="text-align: center; margin-top: 20px;">
		<a href="${pageContext.request.contextPath}/admin/dashboard">Back
			to Dashboard</a>
	</div>
</body>
</html>
