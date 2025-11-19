<%-- 
    Document   : profile.jsp
    Created on : Apr 2, 2025, 10:19:46 AM
    Author     : Chew Jin Ni
--%>

<%@page import="model.Users"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Profile</title>
    <link rel="stylesheet" type="text/css" href="../css/profile_form.css">
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
    <%
        if(session.getAttribute("user") != null){
            Users user = (Users) session.getAttribute("user");
            if(user == null || user.getRole() == null || user.getRole() != Users.Role.Salesman){
                response.sendRedirect("../login.jsp");
                return;
            }
        }else{
            response.sendRedirect("../login.jsp");
            return;
        }
    %>
    <div id="notification" class="error_notification">
        <c:if test="${not empty sessionScope.salesman_errorMsg}">
            ${sessionScope.salesman_errorMsg}
            <% session.removeAttribute("salesman_errorMsg");%>
        </c:if>  
    </div>
    <%@ include file="header.jsp" %>
    <div class="profile-container">
        <h2>Edit Profile</h2>
        <form method="POST" action="${pageContext.request.contextPath}/EditProfile">
            <input type="hidden" name="type" id="type" value="edit_profile" >
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="${not empty sessionScope.user ? sessionScope.user.user_id: ''}" readonly>
            </div>
                    
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${not empty sessionScope.user ? sessionScope.user.name: ''}">
            </div>
            <div class="form-group">
                <label for="ic">IC:</label>
                <input type="text" id="ic" name="ic" value="${not empty sessionScope.user ? sessionScope.user.ic: ''}">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${not empty sessionScope.user ? sessionScope.user.email: ''}">
            </div>
            <div class="form-group">
                <label for="phone">Phone:</label>
                <input type="tel" id="phone" name="phone" value="${not empty sessionScope.user ? sessionScope.user.phone: ''}">
            </div>
            <button type="submit" class="btn-save">Save Changes</button>
            <button type="button" onclick="openPasswordModal()" class="btn-cancel">Change Password</button>
        </form>
    </div>

   <!-- Password Change Modal -->
    <div class="modal" id="passwordModal">
        <div class="modal-content modal-content-half">
            <h3>Change Password</h3>
            <br>
            <form method="POST" action="${pageContext.request.contextPath}/EditProfile">
                <input type="hidden" name="type" id="type" value="edit_password" >
                <div class="form-group">
                    <label for="oldPassword">Old Password:</label>
                    <input type="password" id="oldPassword" name="oldPassword" required>
                </div>
                <div class="form-group">
                    <label for="newPassword">New Password:</label>
                    <input type="password" id="newPassword" name="newPassword" placeholder="Password length more than 8 with uppercase, lowercase, digits and special characters" required>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Confirm New Password:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>
                <button type="submit" class="btn-save">Save Changes</button>
                <button type="button" onclick="closePasswordModal()" class="btn-cancel">Cancel</button>
            </form>
        </div>
    </div>

    <script>
        function openPasswordModal() {
            document.getElementById('passwordModal').style.display = 'block';
        }

        function closePasswordModal() {
            document.getElementById('passwordModal').style.display = 'none';
        }
    </script>
</body>
</html>