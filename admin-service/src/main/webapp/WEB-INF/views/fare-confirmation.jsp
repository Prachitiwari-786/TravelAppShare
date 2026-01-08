<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Fare Confirmation</title>
<style>
.center {
    margin: auto;
    width: 50%;
    border: 2px solid #FFA500;
    padding: 20px;
    font-family: Arial, sans-serif;
}
</style>
<script>
    function confirmBooking() {
        document.getElementById("bookingForm").submit();
    }
</script>
</head>
<body>
<div class="center">
    <h2>Fare Confirmation</h2>

    <c:choose>
        <c:when test="${status eq 'AVAILABLE'}">
            <p>Trip: ${source} → ${destination} (${type})</p>
            <p><b>Fare: ₹${fare}</b></p>

            <form id="bookingForm" action="${pageContext.request.contextPath}/booking/confirm" method="post">
                <input type="hidden" name="userId" value="${loggedUserId}"/>
                <input type="hidden" name="cabId" value="${cabId}"/>
                <button type="button" onclick="confirmBooking()">Confirm Booking</button>
            </form>
        </c:when>
        <c:otherwise>
            <p>${status}</p>
            <a href="${pageContext.request.contextPath}/book-cab">Back</a>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
