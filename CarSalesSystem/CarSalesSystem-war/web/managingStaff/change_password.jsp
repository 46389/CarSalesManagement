<%-- 
    Document   : change_password
    Created on : Apr 8, 2025, 3:38:42 PM
    Author     : Chew Jin Ni
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link rel="stylesheet" href="../css/change_password.css">
    <link rel="stylesheet" type="text/css" href="../css/notification.css">
    <script>
       window.onload = function () {
            let notification = document.getElementById("notification");
            if (notification.textContent.trim() !== "") {
                notification.style.display = "block";
                setTimeout(() => {
                    notification.style.display = "none";
                }, 5000);
            }
        };  
    </script>
</head>
<body>
    <div id="notification" class="error_notification">
        <c:if test="${not empty sessionScope.manager_errorMsg}">
            ${sessionScope.manager_errorMsg}
            <% session.removeAttribute("manager_errorMsg");%>
        </c:if>  
    </div>
    <%@ include file="header.jsp" %>
    <div class="password-container">
        <h2>Change Password</h2>
        <form method="POST" action="${pageContext.request.contextPath}/ChangePassword">
            <div class="form-group">
                <label for="oldPassword">Old Password:</label>
                <input type="password" id="oldPassword" name="oldPassword" required>
            </div>
            <div class="form-group">
                <label for="newPassword">New Password:</label>
                <input type="password" id="newPassword" name="newPassword" required>
                <small class="password-validation">Password must be at least 8 characters long.</small>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirm New Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            <button type="submit" class="btn-save">Save Changes</button>
        </form>
    </div>
</body>
</html>
