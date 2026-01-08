<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<title>Add New Cab</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f6f9;
}

.container {
	width: 50%;
	margin: 40px auto;
	background: #fff;
	border: 2px solid #4682B4;
	border-radius: 8px;
	padding: 20px;
	box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
	text-align: center;
	color: #4682B4;
}

label {
	display: block;
	margin-top: 10px;
	font-weight: bold;
	color: #333;
}

input, select {
	width: 100%;
	padding: 8px;
	margin-top: 5px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

.actions {
	text-align: center;
	margin-top: 20px;
}

button {
	background-color: #4682B4;
	color: white;
	padding: 10px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

button:hover {
	background-color: #315f86;
}

a {
	display: inline-block;
	margin-top: 15px;
	color: #4682B4;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}
</style>
</head>
<body>
	<div class="container">
		<h2>Add New Cab</h2>
		<form action="${pageContext.request.contextPath}/admin/cabs/add"
			method="post">
			<label for="source">Source:</label> <input type="text" id="source"
				name="source" required /> <label for="destination">Destination:</label>
			<input type="text" id="destination" name="destination" required /> <label
				for="type">Type:</label> <select id="type" name="type" required>
				<option value="">--Select--</option>
				<option value="Sedan">Sedan</option>
				<option value="SUV">SUV</option>
				<option value="Hatchback">Hatchback</option>
			</select> 
			
			<label for="kms">No of Kms:</label> 
			<input type="number" id="kms" name="kms" required />
				
			 <label for="farePerKm">Fare per Km:</label>
			<input type="number" id="farePerKm" name="farePerKm" step="0.1" required />

			<div class="actions">
				<button type="submit">Save</button>
			</div>
		</form>
		<div class="actions">
			<a href="${pageContext.request.contextPath}/admin/dashboard">Back
				to Dashboard</a>
		</div>
	</div>
</body>
</html>
