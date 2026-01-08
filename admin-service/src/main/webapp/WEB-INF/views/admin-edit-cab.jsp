<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Cab</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
        }
        .edit-container {
            width: 500px;
            margin: 60px auto;
            background: #fff;
            border: 2px solid #4682B4;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0px 4px 8px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            color: #4682B4;
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 12px;
        }
        input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            width: 100%;
            margin-top: 20px;
            background-color: #4682B4;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #315f86;
        }
        .back-link {
            text-align: center;
            margin-top: 15px;
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
<div class="edit-container">
    <h2>Edit Cab</h2>
    <form action="${pageContext.request.contextPath}/admin/cabs/edit" method="post">
        <input type="hidden" name="id" value="${cab.id}"/>

        <label>Source:</label>
        <input type="text" name="source" value="${cab.source}" required>

        <label>Destination:</label>
        <input type="text" name="destination" value="${cab.destination}" required>

        <label>Type:</label>
        <input type="text" name="type" value="${cab.type}" required>

        <label>No of Kms:</label>
        <input type="number" name="kms" value="${cab.kms}" required>

        <label>Fare per Km:</label>
        <input type="number" name="farePerKm" value="${cab.farePerKm}" step="0.1" required>

        <button type="submit">Update</button>
    </form>

    <div class="back-link">
        <a href="${pageContext.request.contextPath}/admin/dashboard">Back to Dashboard</a>
    </div>
</div>
</body>
</html>
