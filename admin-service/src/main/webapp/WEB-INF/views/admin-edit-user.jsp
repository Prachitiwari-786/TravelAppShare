<form action="${pageContext.request.contextPath}/admin/users/edit" method="post">
    <input type="hidden" name="id" value="${user.id}" />
    <label>Username:</label>
    <input type="text" name="username" value="${user.username}" required /><br>

    <label>Email:</label>
    <input type="email" name="email" value="${user.email}" /><br>

    <label>Role:</label>
    <select name="role">
        <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>USER</option>
        <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
    </select><br>

    <button type="submit">Save Changes</button>
</form>
